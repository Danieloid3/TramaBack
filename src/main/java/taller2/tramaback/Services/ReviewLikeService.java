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


import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewLikeService implements IReviewLikeService {
    @Autowired
    private ReviewLikeRepository reviewLikeRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<ReviewLike> getAllLikesByReviewId(Long reviewId) {
        return reviewLikeRepository.findAll()
                .stream()
                .filter(like -> like.getReview().getId().equals(reviewId))
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
            return null; // Ya existe el like
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
}
