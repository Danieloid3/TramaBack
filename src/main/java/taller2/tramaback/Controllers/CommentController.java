package taller2.tramaback.Controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import taller2.tramaback.Models.Comment;
import taller2.tramaback.Services.ICommentService;
import taller2.tramaback.Services.IReviewService;

import java.util.List;
@RestController
@RequestMapping("trama/")
@CrossOrigin(value = "http://localhost:3000") // React port

public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ICommentService commentService;

    @GetMapping("/comments/review/{reviewId}")
    public List<Comment> getCommentsByReviewId(@PathVariable Long reviewId) {
        logger.info("Obteniendo comentarios para la review {}", reviewId);
        return commentService.getCommentsByReviewId(reviewId);
    }

    @GetMapping("/comments/user/{userId}")
    public List<Comment> getCommentsByUserId(@PathVariable Long userId) {
        logger.info("Obteniendo comentarios para el usuario {}", userId);
        return commentService.getCommentsByUserId(userId);
    }

    @GetMapping("/comments/{id}")
    public Comment getCommentById(@PathVariable Long id) {
        logger.info("Obteniendo comentario con id {}", id);
        return commentService.getCommentById(id);
    }

    @PostMapping("/comments")
    public Comment createComment(@RequestBody Comment comment) {
        logger.info("Creando comentario: {}", comment);
        return commentService.createComment(comment);
    }

    @PutMapping("/comments/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        logger.info("Actualizando comentario con id {}", id);
        return commentService.updateComment(id, comment);
    }

    @DeleteMapping("/comments/{id}")
    public void deleteComment(@PathVariable Long id) {
        logger.info("Eliminando comentario con id {}", id);
        commentService.deleteComment(id);
    }
}

