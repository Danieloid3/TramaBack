package taller2.tramaback.Services;
import java.util.List;
import taller2.tramaback.Models.ReviewLike;
import taller2.tramaback.DTOs.ReviewLikeDTO;
public interface IReviewLikeService {
    List<ReviewLikeDTO> getAllLikesByReviewId(Long reviewId);

    ReviewLike addLikeToReview(Long reviewId, Long userId);

    void removeLikeFromReview(Long reviewId, Long userId);

    boolean isUserLikedReview(Long reviewId, Long userId);

}
