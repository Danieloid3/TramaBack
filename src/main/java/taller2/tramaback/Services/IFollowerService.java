package taller2.tramaback.Services;

import taller2.tramaback.DTOs.FollowerDTO;
import java.util.List;

public interface IFollowerService {
    List<FollowerDTO> getFollowers(Long userId);
    List<FollowerDTO> getFollowing(Long userId);
    FollowerDTO follow(Long followerId, Long followedId);
    void unfollow(Long followerId, Long followedId);
    boolean isFollowing(Long followerId, Long followedId);
}