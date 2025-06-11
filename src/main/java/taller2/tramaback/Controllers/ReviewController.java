package taller2.tramaback.Controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import taller2.tramaback.Models.Review;
import taller2.tramaback.Services.IReviewService;

import java.util.List;

@RestController
@RequestMapping("trama/")
@CrossOrigin(value = "http://localhost:3000") // React port

public class ReviewController {
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private IReviewService reviewService;

    @GetMapping("/reviews")
    public List<Review> getAllReviews() {
        logger.info("Fetching all reviews");
        List<Review> reviews = reviewService.getAllReviews();
        if (reviews.isEmpty()) {
            logger.warn("No reviews found");
        } else {
            reviews.forEach(review -> logger.info(review.toString()));
        }
        return reviews;
    }

    @GetMapping("/reviews/{id}")
    public Review getReviewById(@PathVariable Long id) {
        logger.info("Fetching review with ID: {}", id);
        Review review = reviewService.getReviewById(id);
        if (review == null) {
            logger.warn("Review with ID {} not found", id);
        } else {
            logger.info("Found review: {}", review);
        }
        return review;
    }
    @PostMapping("/reviews/save")
    public Review createReview(@RequestBody Review review) {
        logger.info("Creating review: {}", review);
        Review createdReview = reviewService.createReview(review);
        logger.info("Review created: {}", createdReview);
        return createdReview;
    }
    @PostMapping("/reviews/update/{id}")
    public Review updateReview(@PathVariable Long id, Review review) {
        logger.info("Updating review with ID: {}", id);
        Review updatedReview = reviewService.updateReview(id, review);
        if (updatedReview == null) {
            logger.warn("Review with ID {} not found for update", id);
        } else {
            logger.info("Review updated: {}", updatedReview);
        }
        return updatedReview;
    }
    @GetMapping("/reviews/delete/{id}")
    public void deleteReview(@PathVariable Long id) {
        logger.info("Deleting review with ID: {}", id);
        reviewService.deleteReview(id);
        logger.info("Review with ID {} deleted", id);
    }
    @GetMapping("/reviews/user/{userId}")
    public List<Review> getReviewsByUserId(@PathVariable Long userId) {
        logger.info("Fetching reviews for user with ID: {}", userId);
        List<Review> reviews = reviewService.getReviewsByUserId(userId);
        if (reviews.isEmpty()) {
            logger.warn("No reviews found for user with ID {}", userId);
        } else {
            reviews.forEach(review -> logger.info(review.toString()));
        }
        return reviews;
    }


    // Additional methods for creating, updating, deleting, and fetching reviews can be added here
}
