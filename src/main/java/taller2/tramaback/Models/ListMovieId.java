package taller2.tramaback.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ListMovieId implements Serializable {
    private static final long serialVersionUID = -7528433173087028054L;
    @Column(name = "list_id", nullable = false)
    private Long listId;

    @Column(name = "movie_id", nullable = false)
    private Long movieId;

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ListMovieId entity = (ListMovieId) o;
        return Objects.equals(this.listId, entity.listId) &&
                Objects.equals(this.movieId, entity.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listId, movieId);
    }

}