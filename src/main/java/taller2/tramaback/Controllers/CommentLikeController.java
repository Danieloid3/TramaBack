package taller2.tramaback.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import taller2.tramaback.DTOs.CommentLikeDTO;
import taller2.tramaback.Models.CommentLike;
import taller2.tramaback.Services.ICommentLikeService;
import java.util.List;

@RestController
@RequestMapping("trama/comment-likes")
@CrossOrigin(value = "http://localhost:3000")
public class CommentLikeController {
    @Autowired
    private ICommentLikeService commentLikeService;

    @GetMapping("/comment/{commentId}")
    public List<CommentLikeDTO> getLikesByCommentId(@PathVariable Long commentId) {
        return commentLikeService.getAllLikesByCommentId(commentId);
    }

    @PostMapping("/like")
    public CommentLikeDTO addLikeToComment(@RequestParam Long commentId, @RequestParam Long userId) {
        CommentLike like = commentLikeService.addLikeToComment(commentId, userId);
        if (like == null) return null;
        return new CommentLikeDTO(
                like.getComment().getId(),
                like.getUser().getId(),
                like.getLikedAt()
        );
    }

    @DeleteMapping("/like")
    public void removeLikeFromComment(@RequestParam Long commentId, @RequestParam Long userId) {
        commentLikeService.removeLikeFromComment(commentId, userId);
    }

    @GetMapping("/liked")
    public boolean isUserLikedComment(@RequestParam Long commentId, @RequestParam Long userId) {
        return commentLikeService.isUserLikedComment(commentId, userId);
    }
}