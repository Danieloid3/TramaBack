package taller2.tramaback.DTOs;

import java.time.OffsetDateTime;

public class CommentLikeDTO {
    private Long commentId;
    private Long userId;
    private OffsetDateTime likedAt;

    public CommentLikeDTO() {}

    public CommentLikeDTO(Long commentId, Long userId, OffsetDateTime likedAt) {
        this.commentId = commentId;
        this.userId = userId;
        this.likedAt = likedAt;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
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