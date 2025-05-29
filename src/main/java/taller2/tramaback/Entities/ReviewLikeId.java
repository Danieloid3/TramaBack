package taller2.tramaback.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ReviewLikeId implements Serializable {
    private static final long serialVersionUID = 5564146135456871371L;
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "review_id", nullable = false)
    private Long reviewId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ReviewLikeId entity = (ReviewLikeId) o;
        return Objects.equals(this.userId, entity.userId) &&
                Objects.equals(this.reviewId, entity.reviewId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, reviewId);
    }

}