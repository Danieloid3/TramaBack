// src/main/java/taller2/tramaback/Repositories/FollowerRepository.java
package taller2.tramaback.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import taller2.tramaback.Models.Follower;
import taller2.tramaback.Models.FollowerId;

import java.util.List;

public interface FollowerRepository extends JpaRepository<Follower, FollowerId> {
    List<Follower> findByFollowerId(Long followerId);
    List<Follower> findByFollowedId(Long followedId);
    boolean existsById(FollowerId id);
    void deleteById(FollowerId id);
}