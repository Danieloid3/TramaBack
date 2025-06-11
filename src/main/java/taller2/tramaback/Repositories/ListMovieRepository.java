package taller2.tramaback.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import taller2.tramaback.Models.List;
import taller2.tramaback.Models.ListMovie;
import taller2.tramaback.Models.ListMovieId;
import taller2.tramaback.Models.Movie;

public interface ListMovieRepository extends JpaRepository<ListMovie, ListMovieId> {
    boolean existsByListAndMovie(List list, Movie movie);
}