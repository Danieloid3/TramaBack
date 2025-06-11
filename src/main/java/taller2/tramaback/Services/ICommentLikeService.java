package taller2.tramaback.Services;
import taller2.tramaback.DTOs.CommentLikeDTO;
import taller2.tramaback.Models.CommentLike;
import java.util.List;

public interface ICommentLikeService {
    List<CommentLikeDTO> getAllLikesByCommentId(Long commentId);
    CommentLike addLikeToComment(Long commentId, Long userId);
    void removeLikeFromComment(Long commentId, Long userId);
    boolean isUserLikedComment(Long commentId, Long userId);
}