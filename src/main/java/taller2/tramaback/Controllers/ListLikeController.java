package taller2.tramaback.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import taller2.tramaback.DTOs.ListLikeDTO;
import taller2.tramaback.Models.ListLike;
import taller2.tramaback.Services.IListLikeService;
import java.util.List;

@RestController
@RequestMapping("trama/list-likes")
@CrossOrigin(origins = {"http://localhost:3000", "https://trama-gamma.vercel.app"}, allowCredentials = "true")
public class ListLikeController {
    @Autowired
    private IListLikeService listLikeService;

    @GetMapping("/list/{listId}")
    public List<ListLikeDTO> getLikesByListId(@PathVariable Long listId) {
        return listLikeService.getAllLikesByListId(listId);
    }

    @PostMapping("/like")
    public ListLikeDTO addLikeToList(@RequestParam Long listId, @RequestParam Long userId) {
        ListLike like = listLikeService.addLikeToList(listId, userId);
        if (like == null) return null;
        return new ListLikeDTO(
                like.getList().getId(),
                like.getUser().getId(),
                like.getLikedAt()
        );
    }

    @DeleteMapping("/like")
    public void removeLikeFromList(@RequestParam Long listId, @RequestParam Long userId) {
        listLikeService.removeLikeFromList(listId, userId);
    }

    @GetMapping("/liked")
    public boolean isUserLikedList(@RequestParam Long listId, @RequestParam Long userId) {
        return listLikeService.isUserLikedList(listId, userId);
    }
}