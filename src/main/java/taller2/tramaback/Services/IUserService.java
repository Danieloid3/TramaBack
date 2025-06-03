package taller2.tramaback.Services;
import taller2.tramaback.Models.User;
import java.util.List;

public interface IUserService {

    public List<User> getAllUsers();
    public User getUserById(Long id);
    public User createUser(User user);
    public User updateUser(Long id, User user);
    public void deleteUser(Long id);

    public User findUserByName(String name);

}
