package taller2.tramaback.Services;

import taller2.tramaback.DTOs.ListLikeDTO;
import taller2.tramaback.Models.ListLike;
import java.util.List;

public interface IListLikeService {
    List<ListLikeDTO> getAllLikesByListId(Long listId);
    ListLike addLikeToList(Long listId, Long userId);
    void removeLikeFromList(Long listId, Long userId);
    boolean isUserLikedList(Long listId, Long userId);
}