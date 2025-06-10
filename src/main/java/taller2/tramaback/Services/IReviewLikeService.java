package taller2.tramaback.Services;
import java.util.List;
import taller2.tramaback.Models.ReviewLike;
public interface IReviewLikeService {
    List<ReviewLike> getAllLikesByReviewId(Long reviewId);

    ReviewLike addLikeToReview(Long reviewId, Long userId);

    void removeLikeFromReview(Long reviewId, Long userId);

    boolean isUserLikedReview(Long reviewId, Long userId);

}
