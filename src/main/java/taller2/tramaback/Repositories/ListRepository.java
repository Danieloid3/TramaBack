package taller2.tramaback.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import taller2.tramaback.Models.List;

import java.util.Optional;

public interface ListRepository extends JpaRepository<List, Long> {
    Optional<List> findByUserIdAndIsFavoritesTrue(Long userId);
}