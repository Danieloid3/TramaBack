package taller2.tramaback.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taller2.tramaback.Models.Review;
import taller2.tramaback.Models.ReviewLike;
import taller2.tramaback.Models.ReviewLikeId;
import taller2.tramaback.Models.User;
import taller2.tramaback.Repositories.ReviewLikeRepository;
import taller2.tramaback.Repositories.ReviewRepository;
import taller2.tramaback.Repositories.UserRepository;
import taller2.tramaback.DTOs.ReviewLikeDTO;
import taller2.tramaback.DTOs.ReviewResponseDTO;

import java.time.OffsetDateTime;
import java.time.ZoneId; // Necesario para la conversión de LocalDate a Date
import java.time.ZoneOffset; // Necesario para la conversión de LocalDate a OffsetDateTime
import java.util.Date; // Necesario para la conversión de LocalDate a Date
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Objects;

@Service
public class ReviewLikeService implements IReviewLikeService {
    @Autowired
    private ReviewLikeRepository reviewLikeRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;


    private ReviewLikeDTO toDTO(ReviewLike like) {
        return new ReviewLikeDTO(
                like.getReview().getId(),
                like.getUser().getId(),
                like.getLikedAt()
        );
    }

    @Override
    public List<ReviewLikeDTO> getAllLikesByReviewId(Long reviewId) {
        return reviewLikeRepository.findAll()
                .stream()
                .filter(like -> like.getReview().getId().equals(reviewId))
                .map(this::toDTO)
                .toList();
    }

    @Override
    public ReviewLike addLikeToReview(Long reviewId, Long userId) {
        Optional<Review> reviewOpt = reviewRepository.findById(reviewId);
        Optional<User> userOpt = userRepository.findById(userId);
        if (reviewOpt.isEmpty() || userOpt.isEmpty()) {
            return null;
        }
        ReviewLikeId id = new ReviewLikeId(userId, reviewId);
        if (reviewLikeRepository.existsById(id)) {
            removeLikeFromReview(reviewId, userId);
            return null;
        }
        ReviewLike like = new ReviewLike();
        like.setId(id);
        like.setUser(userOpt.get());
        like.setReview(reviewOpt.get());
        like.setLikedAt(OffsetDateTime.now());
        return reviewLikeRepository.save(like);
    }

    @Override
    public void removeLikeFromReview(Long reviewId, Long userId) {
        ReviewLikeId id = new ReviewLikeId(userId, reviewId);
        if (reviewLikeRepository.existsById(id)) {
            reviewLikeRepository.deleteById(id);
        }
    }

    @Override
    public boolean isUserLikedReview(Long reviewId, Long userId) {
        ReviewLikeId id = new ReviewLikeId(userId, reviewId);
        return reviewLikeRepository.existsById(id);
    }

    private ReviewResponseDTO mapReviewToResponseDTO(Review review) {
        if (review == null) return null;
        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.setId(review.getId());
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
        // Conversión de Double a Integer (puede perder precisión)
        if (review.getRating() != null) {
            dto.setRating(review.getRating().intValue());
        }
        // Conversión de LocalDate a OffsetDateTime
        if (review.getPublishedDate() != null) {
            // Asumiendo que review.getPublishedDate() devuelve LocalDate. Si ya es OffsetDateTime, esta conversión no es necesaria.
            // Si es LocalDate, se convierte a OffsetDateTime al inicio del día en UTC.
            dto.setPublishedDate(review.getPublishedDate().atStartOfDay().atOffset(ZoneOffset.UTC));
        }
        dto.setImageUrl(review.getImageUrl());
        // Asumiendo que review.getUpdatedAt() ya es OffsetDateTime
        dto.setUpdatedAt(review.getUpdatedAt());
        return dto;
    }

    @Override
    public List<ReviewResponseDTO> getLikedReviewsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return Collections.emptyList();
        }

        List<ReviewLike> userLikes = reviewLikeRepository.findByUser(user);

        return userLikes.stream()
                .map(ReviewLike::getReview)
                .map(this::mapReviewToResponseDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}