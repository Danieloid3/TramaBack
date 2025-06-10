package taller2.tramaback.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import taller2.tramaback.Models.ReviewLike;
import taller2.tramaback.Models.ReviewLikeId;

public interface ReviewLikeRepository  extends JpaRepository<ReviewLike, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, para buscar likes por review o usuario
    ReviewLike findByUserId(Long userId);

    boolean existsById(ReviewLikeId id);

    void deleteById(ReviewLikeId id);
    //ReviewLike existsByUserIdAndReviewId(Long UserId, Long reviewId);


    //Hola dani, tqm
}
