package taller2.tramaback.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import taller2.tramaback.Models.ListLike;
import taller2.tramaback.Models.ListLikeId;

import java.util.List;

public interface ListLikeRepository extends JpaRepository<ListLike, ListLikeId> {
    List<ListLike> findByListId(Long listId);
    boolean existsById(ListLikeId id);
    void deleteById(ListLikeId id);
}