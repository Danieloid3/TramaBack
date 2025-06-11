
package taller2.tramaback.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taller2.tramaback.Models.ListLike;
import taller2.tramaback.Models.ListLikeId;
import taller2.tramaback.Models.User;
import taller2.tramaback.Repositories.ListLikeRepository;
import taller2.tramaback.Repositories.ListRepository;
import taller2.tramaback.Repositories.UserRepository;
import taller2.tramaback.DTOs.ListLikeDTO;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ListLikeService implements IListLikeService {
    @Autowired
    private ListLikeRepository listLikeRepository;
    @Autowired
    private ListRepository listRepository;
    @Autowired
    private UserRepository userRepository;

    private ListLikeDTO toDTO(ListLike like) {
        return new ListLikeDTO(
                like.getList().getId(),
                like.getUser().getId(),
                like.getLikedAt()
        );
    }

    @Override
    public List<ListLikeDTO> getAllLikesByListId(Long listId) {
        return listLikeRepository.findAll()
                .stream()
                .filter(like -> like.getList().getId().equals(listId))
                .map(this::toDTO)
                .toList();
    }

    @Override
    public ListLike addLikeToList(Long listId, Long userId) {
        Optional<taller2.tramaback.Models.List> listOpt = listRepository.findById(listId);
        Optional<User> userOpt = userRepository.findById(userId);
        if (listOpt.isEmpty() || userOpt.isEmpty()) {
            return null;
        }
        ListLikeId id = new ListLikeId();
        id.setUserId(userId);
        id.setListId(listId);
        if (listLikeRepository.existsById(id)) {
            removeLikeFromList(listId, userId);
            return null;
        }
        ListLike like = new ListLike();
        like.setId(id);
        like.setUser(userOpt.get());
        like.setList(listOpt.get());
        like.setLikedAt(OffsetDateTime.now());
        return listLikeRepository.save(like);
    }

    @Override
    public void removeLikeFromList(Long listId, Long userId) {
        ListLikeId id = new ListLikeId();
        id.setUserId(userId);
        id.setListId(listId);
        if (listLikeRepository.existsById(id)) {
            listLikeRepository.deleteById(id);
        }
    }

    @Override
    public boolean isUserLikedList(Long listId, Long userId) {
        ListLikeId id = new ListLikeId();
        id.setUserId(userId);
        id.setListId(listId);
        return listLikeRepository.existsById(id);
    }
}