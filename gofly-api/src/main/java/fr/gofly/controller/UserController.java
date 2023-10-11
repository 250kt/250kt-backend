package fr.gofly.controller;

import fr.gofly.model.User;
import fr.gofly.repository.UserRepository;
import fr.gofly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import fr.gofly.exception.entity.user.UserNotFoundException;

import java.util.Optional;

/**
 * Controller for user-related operations.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    /**
     * Create a new user.
     *
     * @param newUser The user information to create.
     * @return The created user.
     */
    @PostMapping()
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
    @GetMapping("/{userId}")
    User one(@PathVariable String userId){
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    /**
     * Replace an existing user's information with new data.
     *
     * @param newUser The new user information.
     * @return The updated user.
     */
    @PutMapping()
    Optional<User> replaceUser(@RequestBody User newUser) {
        return userService.putUser(newUser);
    }

    /**
     * Delete a user by their identifier (userId).
     *
     * @param userId The identifier of the user to delete.
     */
    @DeleteMapping("/{userId}")
    void deleteUser(@PathVariable String userId) {
        userService.DeleteUser(userId);
    }
}
