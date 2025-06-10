package taller2.tramaback.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taller2.tramaback.Models.Comment;
import taller2.tramaback.Repositories.CommentRepository;
import java.util.List;




@Service
public class CommentService implements ICommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> getCommentsByReviewId(Long reviewId) {
        return commentRepository.findByReviewId(reviewId);
    }

    @Override
    public Comment getCommentById(Long id) {
        return null;
    }

    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Long id, Comment comment) {
        Comment existingComment = commentRepository.findById(id).orElse(null);
        if (existingComment != null) {
            existingComment.setContent(comment.getContent());
            existingComment.setUpdatedAt(java.time.OffsetDateTime.now());
            // Puedes actualizar otros campos si es necesario
            return commentRepository.save(existingComment);
        }
        return null;
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }


     @Override
    public List<Comment> getCommentsByUserId(Long userId) {
        return commentRepository.findByUserId(userId);
    }


}
