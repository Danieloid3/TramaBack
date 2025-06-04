package taller2.tramaback.Services;
import taller2.tramaback.Models.Movie;
import java.util.List;
import java.util.Map;

public interface IMovieService {
    List<Movie> getAllMovies();
    Movie getMovieById(Long id);
    Movie mapToMovie(Map<String, Object> data);


    //List<Movie> getMoviesByUserId(Long userId);
    //List<Movie> getMoviesByGenre(String genre);
    List<Movie> getMoviesByTitle(String title);
}
