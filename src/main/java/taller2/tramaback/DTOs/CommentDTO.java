package taller2.tramaback.DTOs;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class CommentDTO {
    private Long id;
    private Long userId;
    private Long reviewId;
    private Long repliedCommentId;
    private String content;
    private LocalDate publishedDate;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public CommentDTO() {
    }

    public CommentDTO(Long id, Long userId, Long reviewId, Long repliedCommentId, String content,
                      LocalDate publishedDate, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.reviewId = reviewId;
        this.repliedCommentId = repliedCommentId;
        this.content = content;
        this.publishedDate = publishedDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getReviewId() {
        return reviewId;
    }
    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }
    public Long getRepliedCommentId() {
        return repliedCommentId;
    }
    public void setRepliedCommentId(Long repliedCommentId) {
        this.repliedCommentId = repliedCommentId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public LocalDate getPublishedDate() {
        return publishedDate;
    }
    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
