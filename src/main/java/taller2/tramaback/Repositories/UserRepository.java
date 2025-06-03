package taller2.tramaback.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import taller2.tramaback.Models.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
