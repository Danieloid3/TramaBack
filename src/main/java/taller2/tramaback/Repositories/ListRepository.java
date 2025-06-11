package taller2.tramaback.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import taller2.tramaback.Models.List;
import taller2.tramaback.Models.User;
import java.util.Optional;

public interface ListRepository extends JpaRepository<List, Long> {
    java.util.List<List> findByUser(User user); // Nuevo método
    Optional<List> findByUserIdAndIsFavoritesTrue(Long userId);
    // Otros métodos existentes si los hubiera...
}