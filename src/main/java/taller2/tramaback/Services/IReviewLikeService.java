package taller2.tramaback.Services;

import taller2.tramaback.DTOs.ReviewLikeDTO;
import taller2.tramaback.DTOs.ReviewResponseDTO; // Importar
import taller2.tramaback.Models.ReviewLike;

import java.util.List;

public interface IReviewLikeService {
    List<ReviewLikeDTO> getAllLikesByReviewId(Long reviewId);

    ReviewLike addLikeToReview(Long reviewId, Long userId);

    void removeLikeFromReview(Long reviewId, Long userId);

    boolean isUserLikedReview(Long reviewId, Long userId);

    List<ReviewResponseDTO> getLikedReviewsByUserId(Long userId);
}