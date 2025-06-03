package taller2.tramaback.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taller2.tramaback.Models.Review;
import taller2.tramaback.Repositories.ReviewRepository;
import java.util.List;
@Service
public class ReviewService implements IReviewService {
    @Autowired
    private ReviewRepository ReviewRepository;

    @Override
    public List<Review> getAllReviews() {
        return ReviewRepository.findAll();
    }
    @Override
    public Review getReviewById(Long id) {
        return ReviewRepository.findById(id).orElse(null);
    }
    @Override
    public Review createReview(Review review) {
        return ReviewRepository.save(review);
    }
    @Override
    public Review updateReview(Long id, Review review) {
        Review existingReview = ReviewRepository.findById(id).orElse(null);
        if (existingReview != null) {
            existingReview.setContent(review.getContent());
            existingReview.setRating(review.getRating());
            existingReview.setUser(review.getUser());
            return ReviewRepository.save(existingReview);
        }
        return null;
    }
    @Override
    public void deleteReview(Long id) {
        ReviewRepository.deleteById(id);
    }
    @Override
    public List<Review> getReviewsByUserId(Long userId) {
        return ReviewRepository.findByUserId(userId);
    }
    //@Override
    //public List<Review> getReviewsByMovieId(Long movieId) {

      //  return ReviewRepository.findByMovieId(movieId);
    //}



}
