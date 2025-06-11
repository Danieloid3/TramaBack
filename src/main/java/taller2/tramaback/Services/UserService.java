package taller2.tramaback.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Importar PasswordEncoder
import org.springframework.stereotype.Service;
import taller2.tramaback.Repositories.UserRepository;
import java.util.List;
import taller2.tramaback.Models.User;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired // Inyectar PasswordEncoder
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User createUser(User user) {
        // Encriptar la contraseña antes de guardarla
        // Asumimos que user.getPasswordHash() contiene la contraseña en texto plano en este punto
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User userDetails) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setName(userDetails.getName());
            existingUser.setEmail(userDetails.getEmail());
            // Si se proporciona una nueva contraseña, encriptarla
            if (userDetails.getPasswordHash() != null && !userDetails.getPasswordHash().isEmpty()) {
                existingUser.setPasswordHash(passwordEncoder.encode(userDetails.getPasswordHash()));
            }
            existingUser.setProfilePicture(userDetails.getProfilePicture());
            existingUser.setIsActive(userDetails.getIsActive());
            // El rol no se actualiza aquí, puedes añadirlo si es necesario
            // existingUser.setRole(userDetails.getRole());
            return userRepository.save(existingUser);
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findUserByName(String name) {
        // Esta implementación puede ser ineficiente para muchos usuarios
        return userRepository.findAll().stream()
                .filter(user -> user.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    // Método para buscar por email, útil para la lógica de negocio si es necesario
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}