// src/main/java/taller2/tramaback/Controllers/FollowerController.java
package taller2.tramaback.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import taller2.tramaback.DTOs.FollowerDTO;
import taller2.tramaback.Services.FollowerService;

import java.util.List;

@RestController
@RequestMapping("trama/followers")
@CrossOrigin(value = "http://localhost:3000")
public class FollowerController {
    @Autowired
    private FollowerService followerService;

    @GetMapping("/followers/{userId}")
    public List<FollowerDTO> getFollowers(@PathVariable Long userId) {
        return followerService.getFollowers(userId);
    }

    @GetMapping("/following/{userId}")
    public List<FollowerDTO> getFollowing(@PathVariable Long userId) {
        return followerService.getFollowing(userId);
    }

    @PostMapping("/follow")
    public FollowerDTO follow(@RequestParam Long followerId, @RequestParam Long followedId) {
        return followerService.follow(followerId, followedId);
    }

    @DeleteMapping("/unfollow")
    public void unfollow(@RequestParam Long followerId, @RequestParam Long followedId) {
        followerService.unfollow(followerId, followedId);
    }

    @GetMapping("/is-following")
    public boolean isFollowing(@RequestParam Long followerId, @RequestParam Long followedId) {
        return followerService.isFollowing(followerId, followedId);
    }
}