package taller2.tramaback.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import taller2.tramaback.Models.Comment;



import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByReviewId(Long reviewId);
    List<Comment> findByUserId(Long userId);
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, para buscar comentarios por película o usuario

}
