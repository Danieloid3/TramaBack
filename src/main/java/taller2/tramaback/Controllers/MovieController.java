package taller2.tramaback.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taller2.tramaback.Models.Movie;
import taller2.tramaback.Services.IMovieService;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final IMovieService movieService;

    public MovieController(IMovieService movieService) {
        logger.info("Initializing MovieController");
        this.movieService = movieService;

    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        try {
            logger.info("Fetching movie with ID: {}", id);
            Movie movie = movieService.getMovieById(id);
            return ResponseEntity.ok(movie);

        } catch (RuntimeException e) {
            logger.error("Error fetching movie with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<MovieSummaryDTO>> getMoviesByTitle(@PathVariable String title) {
        logger.info("Buscando películas con título: {}", title);
        try {
            List<MovieSummaryDTO> movies = movieService.getMoviesByTitle(title);
            if (movies.isEmpty()) {
                logger.warn("No se encontraron películas con el título: {}", title);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(movies);
            }
            return ResponseEntity.ok(movies);
        } catch (Exception e) {
            logger.error("Error al buscar películas por título {}: {}", title, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/popular/{period}")
    public ResponseEntity<List<MovieSummaryDTO>> getPopularMovies(@PathVariable String period) {
        try {
            List<MovieSummaryDTO> movies = movieService.getPopularMovies(period);
            if (movies.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(movies);
            }
            return ResponseEntity.ok(movies);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}


