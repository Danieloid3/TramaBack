package taller2.tramaback.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ListLikeId implements Serializable {
    private static final long serialVersionUID = 5716107982510203474L;
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "list_id", nullable = false)
    private Long listId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ListLikeId entity = (ListLikeId) o;
        return Objects.equals(this.listId, entity.listId) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listId, userId);
    }

}