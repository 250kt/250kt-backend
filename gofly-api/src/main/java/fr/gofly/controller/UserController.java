package fr.gofly.controller;

import fr.gofly.model.User;
import fr.gofly.repository.UserRepository;
import fr.gofly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<User> createUser(@RequestBody User newUser){
        Optional<User> userOptional = userService.createUser(newUser);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
    /**
     * Get a user by their identifier (userId).
     *
     * @param userId The identifier of the user to retrieve.
     * @return The user corresponding to the identifier.
     */
    @GetMapping("/{userId}")
    ResponseEntity<User> getUser(@PathVariable String userId){
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Update an existing user's information with new data.
     *
     * @param newUser The new user information.
     * @return The updated user.
     */
    @PutMapping()
    ResponseEntity<User> updateUser(@RequestBody User newUser) {
        Optional<User> userOptional = userService.putUser(newUser);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    /**
     * Delete a user by their identifier (userId).
     *
     * @param userId The identifier of the user to delete.
     */
    @DeleteMapping("/{userId}")
    ResponseEntity<User> deleteUser(@PathVariable String userId, @RequestBody User userRequest) {
        return userService.deleteUser(userId, userRequest) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
