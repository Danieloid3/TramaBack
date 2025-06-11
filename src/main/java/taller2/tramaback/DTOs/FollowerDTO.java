// src/main/java/taller2/tramaback/DTOs/FollowerDTO.java
package taller2.tramaback.DTOs;

import java.time.OffsetDateTime;

public class FollowerDTO {
    private Long followerId;
    private Long followedId;
    private OffsetDateTime createdAt;

    public FollowerDTO() {}

    public FollowerDTO(Long followerId, Long followedId, OffsetDateTime createdAt) {
        this.followerId = followerId;
        this.followedId = followedId;
        this.createdAt = createdAt;
    }

    public Long getFollowerId() { return followerId; }
    public void setFollowerId(Long followerId) { this.followerId = followerId; }
    public Long getFollowedId() { return followedId; }
    public void setFollowedId(Long followedId) { this.followedId = followedId; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}