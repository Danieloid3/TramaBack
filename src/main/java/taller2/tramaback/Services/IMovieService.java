package taller2.tramaback.Services;
import taller2.tramaback.DTOs.MovieSummaryDTO;
import taller2.tramaback.Models.Movie;
import java.util.List;
import java.util.Map;

public interface IMovieService {
    List<MovieSummaryDTO> getPopularMovies(String period);

    Movie getMovieById(Long id);

    Movie mapToMovie(Map<String, Object> data);


    //List<Movie> getMoviesByUserId(Long userId);
    //List<Movie> getMoviesByGenre(String genre);
    List<MovieSummaryDTO> getMoviesByTitle(String title);
}

