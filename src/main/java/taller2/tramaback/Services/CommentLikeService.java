package taller2.tramaback.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taller2.tramaback.Models.Comment;
import taller2.tramaback.Models.CommentLike;
import taller2.tramaback.Models.CommentLikeId;
import taller2.tramaback.Models.User;
import taller2.tramaback.Repositories.CommentLikeRepository;
import taller2.tramaback.Repositories.CommentRepository;
import taller2.tramaback.Repositories.UserRepository;
import taller2.tramaback.DTOs.CommentLikeDTO;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentLikeService implements ICommentLikeService {
    @Autowired
    private CommentLikeRepository commentLikeRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    private CommentLikeDTO toDTO(CommentLike like) {
        return new CommentLikeDTO(
                like.getComment().getId(),
                like.getUser().getId(),
                like.getLikedAt()
        );
    }

    @Override
    public List<CommentLikeDTO> getAllLikesByCommentId(Long commentId) {
        return commentLikeRepository.findAll()
                .stream()
                .filter(like -> like.getComment().getId().equals(commentId))
                .map(this::toDTO)
                .toList();
    }

    @Override
    public CommentLike addLikeToComment(Long commentId, Long userId) {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        Optional<User> userOpt = userRepository.findById(userId);
        if (commentOpt.isEmpty() || userOpt.isEmpty()) {
            return null;
        }
        CommentLikeId id = new CommentLikeId();
        id.setUserId(userId);
        id.setCommentId(commentId);
        if (commentLikeRepository.existsById(id)) {
            removeLikeFromComment(commentId, userId);
            return null;
        }
        CommentLike like = new CommentLike();
        like.setId(id);
        like.setUser(userOpt.get());
        like.setComment(commentOpt.get());
        like.setLikedAt(OffsetDateTime.now());
        return commentLikeRepository.save(like);
    }

    @Override
    public void removeLikeFromComment(Long commentId, Long userId) {
        CommentLikeId id = new CommentLikeId();
        id.setUserId(userId);
        id.setCommentId(commentId);
        if (commentLikeRepository.existsById(id)) {
            commentLikeRepository.deleteById(id);
        }
    }

    @Override
    public boolean isUserLikedComment(Long commentId, Long userId) {
        CommentLikeId id = new CommentLikeId();
        id.setUserId(userId);
        id.setCommentId(commentId);
        return commentLikeRepository.existsById(id);
    }
}