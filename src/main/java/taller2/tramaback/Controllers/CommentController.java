package taller2.tramaback.Controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import taller2.tramaback.Models.Comment;
import taller2.tramaback.Services.ICommentService;
import taller2.tramaback.Services.IReviewService;
import taller2.tramaback.DTOs.CommentDTO;

import java.util.List;
@RestController
@RequestMapping("trama/")
@CrossOrigin(value = "http://localhost:3000") // React port

public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ICommentService commentService;

    // En CommentController.java
    @GetMapping("/comments/review/{reviewId}")
    public List<CommentDTO> getCommentsByReviewId(@PathVariable Long reviewId) {
        logger.info("Obteniendo comentarios para la review {}", reviewId);
        List<Comment> comments = commentService.getCommentsByReviewId(reviewId);
        return comments.stream().map(comment -> {
            CommentDTO dto = new CommentDTO();
            dto.setId(comment.getId());
            dto.setUserId(comment.getUser().getId());
            dto.setReviewId(comment.getReview() != null ? comment.getReview().getId() : null);
            dto.setRepliedCommentId(comment.getRepliedComment() != null ? comment.getRepliedComment().getId() : null);
            dto.setContent(comment.getContent());
            dto.setPublishedDate(comment.getPublishedDate());
            dto.setCreatedAt(comment.getCreatedAt());
            dto.setUpdatedAt(comment.getUpdatedAt());
            return dto;
        }).toList();
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

