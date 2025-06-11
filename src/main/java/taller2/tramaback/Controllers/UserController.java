package taller2.tramaback.Controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import taller2.tramaback.Models.User;
import taller2.tramaback.Payload.AuthResponse;
import taller2.tramaback.Payload.LoginRequest;
import taller2.tramaback.Security.Jwt.JwtUtil;
import taller2.tramaback.Services.IUserService;
import taller2.tramaback.Services.UserService; // Importar UserService para findUserByEmail

import java.util.List;

@RestController
@RequestMapping("/trama") // Ruta base /trama
// @CrossOrigin(value = "http://localhost:3000") // CORS global configurado en SecurityConfig
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService iUserService; // Interfaz

    @Autowired
    private UserService userService; // Implementación para métodos específicos como findUserByEmail

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    // Endpoint de Login
    @PostMapping("/auth/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        logger.info("Attempting login for email: {}", loginRequest.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // Necesitas una forma de obtener el User completo para obtener el ID y el nombre
        // Asumimos que tienes un método en tu UserService para buscar por email
        User user = userService.findUserByEmail(userDetails.getUsername()); // Usar la implementación UserService

        logger.info("User {} authenticated successfully.", userDetails.getUsername());
        return ResponseEntity.ok(new AuthResponse(jwt, user.getId(), user.getEmail(), user.getName()));
    }

    // Endpoint de Registro (usa el createUser existente)
    // Asegúrate de que la contraseña se envía en texto plano en el campo passwordHash
    // y UserService la encripta.
    @PostMapping("/users/save")
    public User createUser(@RequestBody User user) {
        logger.info("Creating user: {}", user.getEmail()); // Loguear email en lugar de todo el objeto por seguridad
        User createdUser = iUserService.createUser(user);
        logger.info("User created with ID: {}", createdUser.getId());
        // No devuelvas el hash de la contraseña en la respuesta
        createdUser.setPasswordHash(null);
        return createdUser;
    }


    @GetMapping("/users")
    // @PreAuthorize("hasRole('ADMIN')") // Ejemplo de cómo proteger con roles
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        List<User> users = iUserService.getAllUsers();
        if (users.isEmpty()) {
            logger.warn("No users found");
        } else {
            users.forEach(user -> {
                user.setPasswordHash(null); // No exponer hash
                logger.info("User: id={}, email={}", user.getId(), user.getEmail());
            });
        }
        return users;
    }

    // El path /{id} y /{name} pueden ser ambiguos.
    // Spring intentará convertir el path variable a Long. Si falla, probará con String.
    @GetMapping("/users/id/{id}") // Ruta más explícita
    public User getUserById(@PathVariable Long id) {
        logger.info("Fetching user with ID: {}", id);
        User user = iUserService.getUserById(id);
        if (user == null) {
            logger.warn("User with ID {} not found", id);
        } else {
            user.setPasswordHash(null); // No exponer hash
            logger.info("Found user: {}", user.getEmail());
        }
        return user;
    }

    @GetMapping("/users/email/{email}") // Ruta para buscar por email
    public User getUserByEmail(@PathVariable String email) {
        logger.info("Fetching user with email: {}", email);
        User user = userService.findUserByEmail(email); // Usar la implementación UserService
        if (user == null) {
            logger.warn("User with email {} not found", email);
        } else {
            user.setPasswordHash(null); // No exponer hash
            logger.info("Found user: {}", user.getEmail());
        }
        return user;
    }


    // Considera usar PUT para actualizaciones y que el ID venga en el path
    @PutMapping("/users/update/{id}") // Cambiado a PUT y path variable para id
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        logger.info("Updating user with ID: {}", id);
        // Asegúrate de que el ID del path se usa, no el del body si viniera
        User updatedUser = iUserService.updateUser(id, user);
        if (updatedUser == null) {
            logger.warn("User with ID {} not found for update", id);
        } else {
            updatedUser.setPasswordHash(null); // No exponer hash
            logger.info("User updated: {}", updatedUser.getEmail());
        }
        return updatedUser;
    }

    // Considera usar DELETE para borrados
    @DeleteMapping("/users/delete/{id}") // Cambiado a DELETE
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        logger.info("Deleting user with ID: {}", id);
        User user = iUserService.getUserById(id);
        if (user == null) {
            logger.warn("User with ID {} not found for deletion.", id);
            return ResponseEntity.notFound().build();
        }
        iUserService.deleteUser(id);
        logger.info("User with ID {} deleted", id);
        return ResponseEntity.ok("User with ID " + id + " deleted successfully.");
    }

    // Este endpoint puede ser ambiguo con /users/id/{id} si el nombre es numérico
    // Se recomienda una ruta más específica si se mantiene, o usar query params.
    // Por ejemplo: /users/search?name=...
    // O como se hizo con email: /users/name/{name}
    @GetMapping("/users/name/{name}")
    public User findUserByName(@PathVariable String name) {
        logger.info("Finding user by name: {}", name);
        User user = iUserService.findUserByName(name);
        if (user == null) {
            logger.warn("User with name {} not found", name);
        } else {
            user.setPasswordHash(null); // No exponer hash
            logger.info("Found user: {}", user.getEmail());
        }
        return user;
    }
}