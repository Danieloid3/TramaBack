package taller2.tramaback.DTOs;

import java.time.OffsetDateTime;

public class ListLikeDTO {
    private Long listId;
    private Long userId;
    private OffsetDateTime likedAt;

    public ListLikeDTO() {}

    public ListLikeDTO(Long listId, Long userId, OffsetDateTime likedAt) {
        this.listId = listId;
        this.userId = userId;
        this.likedAt = likedAt;
    }

    public Long getListId() { return listId; }
    public void setListId(Long listId) { this.listId = listId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public OffsetDateTime getLikedAt() { return likedAt; }
    public void setLikedAt(OffsetDateTime likedAt) { this.likedAt = likedAt; }
}