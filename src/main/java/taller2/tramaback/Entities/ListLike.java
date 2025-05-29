package taller2.tramaback.Entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.OffsetDateTime;

@Entity
@Table(name = "list_likes")
public class ListLike {
    @EmbeddedId
    private ListLikeId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @MapsId("listId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "list_id", nullable = false)
    private List list;

    @ColumnDefault("now()")
    @Column(name = "liked_at", nullable = false)
    private OffsetDateTime likedAt;

    public ListLikeId getId() {
        return id;
    }

    public void setId(ListLikeId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public OffsetDateTime getLikedAt() {
        return likedAt;
    }

    public void setLikedAt(OffsetDateTime likedAt) {
        this.likedAt = likedAt;
    }

}