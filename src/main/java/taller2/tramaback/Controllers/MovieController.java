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

    }



