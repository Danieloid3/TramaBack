package taller2.tramaback.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import taller2.tramaback.Models.Review;
import taller2.tramaback.Services.IReviewService;
import taller2.tramaback.DTOs.ReviewResponseDTO;

import java.time.ZoneId; // Necesario para la conversión de LocalDate a Date
import java.time.ZoneOffset; // Necesario para la conversión de LocalDate a OffsetDateTime
import java.util.Date; // Necesario para la conversión de LocalDate a Date
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
        // Asumiendo que ReviewResponseDTO tiene un setId(Long id) o se establece en el constructor
        // Si el ID de la review en sí misma es necesario en el DTO:
        // dto.setId(review.getId());

        dto.setUserId(review.getUser().getId());
        dto.setUserName(review.getUser().getName());

        if (review.getMovie() != null) {
            dto.setMovieId(review.getMovie().getId());
            dto.setMovieTitle(review.getMovie().getTitle());
            dto.setMovieDirector(review.getMovie().getDirector());
            // Conversión de LocalDate a java.util.Date
            if (review.getMovie().getReleaseDate() != null) {
                dto.setMovieReleaseDate(Date.from(review.getMovie().getReleaseDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            }
        }

        dto.setContent(review.getContent());
        // Conversión de Double a Integer (puede perder precisión si el rating tiene decimales)
        if (review.getRating() != null) {
            dto.setRating(review.getRating().intValue());
        }
        // Conversión de LocalDate a OffsetDateTime
        if (review.getPublishedDate() != null) {
            dto.setPublishedDate(review.getPublishedDate().atStartOfDay().atOffset(ZoneOffset.UTC));
        }
        dto.setImageUrl(review.getImageUrl());
        dto.setUpdatedAt(review.getUpdatedAt()); // Asumiendo que review.getUpdatedAt() ya es OffsetDateTime
        return dto;
    }

    // Additional methods for creating, updating, deleting, and fetching reviews can be added here
}