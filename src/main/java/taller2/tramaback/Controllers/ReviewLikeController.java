package taller2.tramaback.Controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taller2.tramaback.Models.ReviewLike;
import taller2.tramaback.Services.IReviewLikeService;
// import taller2.tramaback.Services.IReviewService; // No se usa directamente aquí
import taller2.tramaback.DTOs.ReviewLikeDTO;
import taller2.tramaback.DTOs.ReviewResponseDTO; // Importar

import java.util.List;

@RestController
@RequestMapping("trama/review-likes")
@CrossOrigin(origins = {"http://localhost:3000", "https://trama-gamma.vercel.app"}, allowCredentials = "true")
public class ReviewLikeController {
    private static final Logger logger = LoggerFactory.getLogger(ReviewLikeController.class);

    @Autowired
    private IReviewLikeService reviewLikeService;

    @GetMapping("/review/{reviewId}")
    public List<ReviewLikeDTO> getLikesByReviewId(@PathVariable Long reviewId) {
        logger.info("Obteniendo likes para la review {}", reviewId);
        return reviewLikeService.getAllLikesByReviewId(reviewId);
    }

    @PostMapping("/like")
    public ReviewLike addLikeToReview(@RequestParam Long reviewId, @RequestParam Long userId) {
        // Este método en el servicio actualmente devuelve null si el like ya existía (y lo borra)
        // o si el usuario/review no existen. El controlador devuelve ese null.
        // Considera devolver ResponseEntity para manejar mejor estos casos.
        logger.info("Usuario {} da like a la review {}", userId, reviewId);
        return reviewLikeService.addLikeToReview(reviewId, userId);
    }

/*    @DeleteMapping("/like")
    public void removeLikeFromReview(@RequestParam Long reviewId, @RequestParam Long userId) {
        logger.info("Usuario {} quita like de la review {}", userId, reviewId);
        reviewLikeService.removeLikeFromReview(reviewId, userId);
    }*/

    @GetMapping("/liked")
    public boolean isUserLikedReview(@RequestParam Long reviewId, @RequestParam Long userId) {
        logger.info("Verificando si el usuario {} dio like a la review {}", userId, reviewId);
        return reviewLikeService.isUserLikedReview(reviewId, userId);
    }

    @GetMapping("/user/{userId}/liked-reviews") // Nuevo endpoint
    public ResponseEntity<List<ReviewResponseDTO>> getLikedReviewsByUserId(@PathVariable Long userId) {
        logger.info("Obteniendo reviews que le gustaron al usuario {}", userId);
        List<ReviewResponseDTO> likedReviews = reviewLikeService.getLikedReviewsByUserId(userId);
        if (likedReviews == null || likedReviews.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(likedReviews);
    }
}