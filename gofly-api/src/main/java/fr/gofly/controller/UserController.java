package fr.gofly.controller;

import fr.gofly.model.User;
import fr.gofly.repository.UserRepository;
import fr.gofly.service.UserService;
import org.springframework.web.bind.annotation.*;
import fr.gofly.exception.entity.user.UserNotFoundException;

/**
 * Controller for user-related operations.
 */
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    /**
     * Constructor for the UserController.
     *
     * @param userRepository The user repository.
     * @param userService    The user service.
     */
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    /**
     * Create a new user.
     *
     * @param newUser The user information to create.
     * @return The created user.
     */
    @PostMapping("/users")
    public User newUser(@RequestBody User newUser){
        return userService.createUser(newUser);
    }

    /**
     * Get a user by their identifier (userId).
     *
     * @param userId The identifier of the user to retrieve.
     * @return The user corresponding to the identifier.
     * @throws UserNotFoundException If no matching user is found.
     */
    @GetMapping("/users/{userId}")
    User one(@PathVariable String userId){
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    /**
     * Replace an existing user's information with new data.
     *
     * @param newUser The new user information.
     * @param userId  The identifier of the user to update.
     * @return The updated user.
     */
    @PutMapping("/users/{userId}")
    User replaceUser(@RequestBody User newUser, @PathVariable String userId) {
        return userService.putUser(newUser, userId);
    }

    /**
     * Delete a user by their identifier (userId).
     *
     * @param userId The identifier of the user to delete.
     */
    @DeleteMapping("/users/{userId}")
    void deleteUser(@PathVariable String userId) {
        userService.DeleteUser(userId);
    }
}
