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

        public MovieService(MovieRepository movieRepository) {
            this.movieRepository = movieRepository;
            this.restTemplate = new RestTemplate();
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
            movie.setTrailerUrl("https://www.youtube.com/watch?v=" + data.get("video_key"));// Luego puedes expandir esto
            movie.setDuration(data.get("runtime") != null ? ((Number) data.get("runtime")).doubleValue() : null);
            movie.setCountry(null);
            movie.setDirector(null);
            return movie;

        }
    }

