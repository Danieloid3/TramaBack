package taller2.tramaback.Controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import taller2.tramaback.Models.Review;
import taller2.tramaback.Services.IReviewService;
import taller2.tramaback.DTOs.ReviewResponseDTO;

import java.util.List;

@RestController
@RequestMapping("trama/")

@CrossOrigin(value = "http://localhost:3001") // React port

public class ReviewController {
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private IReviewService reviewService;

    @GetMapping("/reviews")
    public List<ReviewResponseDTO> getAllReviews() {
        return reviewService.getAllReviews().stream()
                .map(this::mapToDTO)
                .toList();
    }

    @GetMapping("/reviews/{id}")
    public ReviewResponseDTO getReviewById(@PathVariable Long id) {
        Review review = reviewService.getReviewById(id);
        return review != null ? mapToDTO(review) : null;
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
    public List<ReviewResponseDTO> getReviewsByUserId(@PathVariable Long userId) {
        return reviewService.getReviewsByUserId(userId).stream()
                .map(this::mapToDTO)
                .toList();
    }
    private ReviewResponseDTO mapToDTO(Review review) {
        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.setUserId(review.getUser().getId());
        dto.setUserName(review.getUser().getName());
        if (review.getMovie() != null) {
            dto.setMovieId(review.getMovie().getId());
            dto.setMovieTitle(review.getMovie().getTitle());
            dto.setMovieDirector(review.getMovie().getDirector());
            dto.setMovieReleaseDate(review.getMovie().getReleaseDate());
        }
        dto.setContent(review.getContent());
        dto.setRating(review.getRating());
        dto.setPublishedDate(review.getPublishedDate());
        dto.setImageUrl(review.getImageUrl());
        dto.setUpdatedAt(review.getUpdatedAt());
        return dto;
    }


    // Additional methods for creating, updating, deleting, and fetching reviews can be added here
}
