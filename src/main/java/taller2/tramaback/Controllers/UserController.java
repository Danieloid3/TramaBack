package taller2.tramaback.Controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import taller2.tramaback.Services.IUserService;
import taller2.tramaback.Models.User;

import java.util.List;

@RestController
//http://localhost:5000/trama/
@RequestMapping("trama")
@CrossOrigin(value = "http://localhost:3000")
//puerto de REACT 3000
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            logger.warn("No users found");
        } else {
            users.forEach(user -> logger.info(user.toString()));
        }
        return users;

    }
    @GetMapping("/users/{id}")
    public User getUserById(Long id) {
        logger.info("Fetching user with ID: {}", id);
        User user = userService.getUserById(id);
        if (user == null) {
            logger.warn("User with ID {} not found", id);
        } else {
            logger.info("Found user: {}", user);
        }
        return user;
    }

    @PostMapping("/users/save")
    public User createUser(@RequestBody User user) {
        logger.info("Creating user: {}", user);
        User createdUser = userService.createUser(user);
        logger.info("User created: {}", createdUser);
        return createdUser;
    }

    @PostMapping("/users/update/{id}")
    public User updateUser(Long id, User user) {
        logger.info("Updating user with ID: {}", id);
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser == null) {
            logger.warn("User with ID {} not found for update", id);
        } else {
            logger.info("User updated: {}", updatedUser);
        }
        return updatedUser;
    }

    @GetMapping("/users/delete/{id}")
    public void deleteUser(Long id) {
        logger.info("Deleting user with ID: {}", id);
        userService.deleteUser(id);
        logger.info("User with ID {} deleted", id);
    }

    @GetMapping("/users/{name}")
    public User findUserByName(String name) {
        logger.info("Finding user by name: {}", name);
        User user = userService.findUserByName(name);
        if (user == null) {
            logger.warn("User with name {} not found", name);
        } else {
            logger.info("Found user: {}", user);
        }
        return user;
    }



}
