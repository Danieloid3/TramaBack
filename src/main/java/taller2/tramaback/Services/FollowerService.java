// src/main/java/taller2/tramaback/Services/FollowerService.java
package taller2.tramaback.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taller2.tramaback.DTOs.FollowerDTO;
import taller2.tramaback.Models.Follower;
import taller2.tramaback.Models.FollowerId;
import taller2.tramaback.Models.User;
import taller2.tramaback.Repositories.FollowerRepository;
import taller2.tramaback.Repositories.UserRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FollowerService implements IFollowerService {

    @Autowired
    private FollowerRepository followerRepository;

    @Autowired
    private UserRepository userRepository;

    private FollowerDTO toDTO(Follower follower) {
        return new FollowerDTO(
                follower.getFollower().getId(),
                follower.getFollowed().getId(),
                follower.getCreatedAt()
        );
    }

    @Override
    public List<FollowerDTO> getFollowers(Long userId) {
        return followerRepository.findByFollowedId(userId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FollowerDTO> getFollowing(Long userId) {
        return followerRepository.findByFollowerId(userId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FollowerDTO follow(Long followerId, Long followedId) {
        if (followerId.equals(followedId)) return null;
        Optional<User> followerOpt = userRepository.findById(followerId);
        Optional<User> followedOpt = userRepository.findById(followedId);
        if (followerOpt.isEmpty() || followedOpt.isEmpty()) return null;

        FollowerId id = new FollowerId();
        id.setFollowerId(followerId);
        id.setFollowedId(followedId);

        if (followerRepository.existsById(id)) return null;

        Follower follower = new Follower();
        follower.setId(id);
        follower.setFollower(followerOpt.get());
        follower.setFollowed(followedOpt.get());
        follower.setCreatedAt(OffsetDateTime.now());

        return toDTO(followerRepository.save(follower));
    }

    @Override
    public void unfollow(Long followerId, Long followedId) {
        FollowerId id = new FollowerId();
        id.setFollowerId(followerId);
        id.setFollowedId(followedId);
        if (followerRepository.existsById(id)) {
            followerRepository.deleteById(id);
        }
    }

    @Override
    public boolean isFollowing(Long followerId, Long followedId) {
        FollowerId id = new FollowerId();
        id.setFollowerId(followerId);
        id.setFollowedId(followedId);
        return followerRepository.existsById(id);
    }
}