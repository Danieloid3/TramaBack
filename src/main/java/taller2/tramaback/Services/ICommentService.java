package taller2.tramaback.Services;
import taller2.tramaback.Models.Comment;
import java.util.List;
public interface ICommentService {
    List<Comment> getAllComments();
    Comment getCommentById(Long id);
    Comment createComment(Comment comment);
    Comment updateComment(Long id, Comment comment);
    void deleteComment(Long id);
    List<Comment> getCommentsByUserId(Long userId);
    List<Comment> getCommentsByReviewId(Long reviewId);
}
