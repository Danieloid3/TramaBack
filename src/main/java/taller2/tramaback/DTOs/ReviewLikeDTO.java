package taller2.tramaback.DTOs;

import java.time.OffsetDateTime;

public class ReviewLikeDTO {
    private Long reviewId;
    private Long userId;
    private OffsetDateTime likedAt;

    public ReviewLikeDTO() {}

    public ReviewLikeDTO(Long reviewId, Long userId, OffsetDateTime likedAt) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.likedAt = likedAt;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public OffsetDateTime getLikedAt() {
        return likedAt;
    }

    public void setLikedAt(OffsetDateTime likedAt) {
        this.likedAt = likedAt;
    }
}