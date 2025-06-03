package taller2.tramaback.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import taller2.tramaback.Models.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Custom query to find reviews by user ID
    List<Review> findByUserId(Long userId);
    // Custom query to find reviews by rating

}
