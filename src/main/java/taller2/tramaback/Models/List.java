package taller2.tramaback.Models;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "lists")
public class List {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('lists_id_seq')")
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @ColumnDefault("false")
    @Column(name = "is_favorites", nullable = false)
    private Boolean isFavorites = false;

    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @ColumnDefault("now()")
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsFavorites() {
        return isFavorites;
    }

    public void setIsFavorites(Boolean isFavorites) {
        this.isFavorites = isFavorites;
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


    @PrePersist
    public void prePersist() {
        this.createdAt = ZonedDateTime.now().toOffsetDateTime();
        this.updatedAt = ZonedDateTime.now().toOffsetDateTime();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = ZonedDateTime.now().toOffsetDateTime();
    }

}