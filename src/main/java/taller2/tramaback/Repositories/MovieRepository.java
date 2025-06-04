package taller2.tramaback.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import taller2.tramaback.Models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
