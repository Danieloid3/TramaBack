package taller2.tramaback.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import taller2.tramaback.Models.Movie;
import taller2.tramaback.Repositories.MovieRepository;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.List;

@Service
public class MovieService implements IMovieService {

    private final MovieRepository movieRepository;
    private final RestTemplate restTemplate;

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.url}")
    private String apiUrl;

    public MovieService(MovieRepository movieRepository, RestTemplate restTemplate) {
        this.movieRepository = movieRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getMovieById(Long id) {
        // 1. Buscar en base de datos
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            return optionalMovie.get();
        }

        // 2. No estaba, buscar en TMDB
        String url = apiUrl + "/movie/" + id + "?api_key=" + apiKey + "&language=es-ES";

        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            Map<String, Object> data = response.getBody();

            if (data == null || data.get("id") == null) {
                throw new RuntimeException("No se encontró la película en TMDB");
            }

            Movie movie = mapToMovie(data);
            movieRepository.save(movie); // Guardar en base de datos
            return movie;

        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la película: " + e.getMessage());
        }
    }

    @Override
    public List<Movie> getMoviesByTitle(String title) {
        return List.of();
    }

    @Override
    public Movie mapToMovie(Map<String, Object> data) {
        Movie movie = new Movie();
        movie.setId(((Number) data.get("id")).longValue());
        movie.setTitle((String) data.get("title"));
        movie.setSynopsis((String) data.get("overview"));
        movie.setReleaseDate(data.get("release_date") != null ? LocalDate.parse((String) data.get("release_date")) : null);
        movie.setLanguage((String) data.get("original_language"));
        movie.setPosterUrl(data.get("poster_path") != null ? "https://image.tmdb.org/t/p/w500" + data.get("poster_path") : null);

        // Duración (runtime)
        if (data.get("runtime") != null) {
            movie.setDuration(((Number) data.get("runtime")).doubleValue());
        }

        // País de origen (primer país de producción)
        if (data.get("production_countries") != null) {
            List<Map<String, Object>> countries = (List<Map<String, Object>>) data.get("production_countries");
            if (!countries.isEmpty()) {
                movie.setCountry((String) countries.get(0).get("name"));
            }
        }

        // Obtener datos de créditos para el director
        String creditsUrl = apiUrl + "/movie/" + movie.getId() + "/credits?api_key=" + apiKey + "&language=es-ES";
        try {
            ResponseEntity<Map> creditsResponse = restTemplate.getForEntity(creditsUrl, Map.class);
            Map<String, Object> creditsData = creditsResponse.getBody();
            if (creditsData != null && creditsData.get("crew") != null) {
                List<Map<String, Object>> crew = (List<Map<String, Object>>) creditsData.get("crew");
                // Buscar el director
                for (Map<String, Object> member : crew) {
                    if ("Director".equals(member.get("job"))) {
                        movie.setDirector((String) member.get("name"));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            // Si hay error al obtener créditos, continuar con lo que tenemos
        }

        // Obtener videos para el trailer
        String videosUrl = apiUrl + "/movie/" + movie.getId() + "/videos?api_key=" + apiKey;
        try {
            ResponseEntity<Map> videosResponse = restTemplate.getForEntity(videosUrl, Map.class);
            Map<String, Object> videosData = videosResponse.getBody();
            if (videosData != null && videosData.get("results") != null) {
                List<Map<String, Object>> videos = (List<Map<String, Object>>) videosData.get("results");
                // Buscar un trailer oficial en YouTube
                for (Map<String, Object> video : videos) {
                    if ("YouTube".equals(video.get("site")) &&
                       ("Trailer".equals(video.get("type")) || "Teaser".equals(video.get("type")))) {
                        movie.setTrailerUrl("https://www.youtube.com/watch?v=" + video.get("key"));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            // Si hay error al obtener videos, continuar con lo que tenemos
        }

        return movie;
    }
}