package taller2.tramaback.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import taller2.tramaback.Models.CommentLike;
import taller2.tramaback.Models.CommentLikeId;
import java.util.List;

public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikeId> {
    List<CommentLike> findByCommentId(Long commentId);
    boolean existsById(CommentLikeId id);
    void deleteById(CommentLikeId id);
}