package taller2.tramaback.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import taller2.tramaback.Models.ReviewLike;
import taller2.tramaback.Models.ReviewLikeId;
import taller2.tramaback.Models.User; // Asegúrate de importar User
import java.util.List; // Asegúrate que es java.util.List

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, ReviewLikeId> {
    List<ReviewLike> findByUser(User user); // Nuevo método
    // Otros métodos existentes si los hubiera...
}