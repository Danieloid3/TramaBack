package taller2.tramaback.Services;
import taller2.tramaback.Models.Review;
import java.util.List;

public interface IReviewService {
    List<Review> getAllReviews();
    Review getReviewById(Long id);
    Review createReview(Review review);
    Review updateReview(Long id, Review review);
    void deleteReview(Long id);
    List<Review> getReviewsByUserId(Long userId);
}
