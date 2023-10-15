package fr.gofly.controller;

import fr.gofly.helper.UserHelper;
import fr.gofly.model.User;
import fr.gofly.repository.UserRepository;
import fr.gofly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller for user-related operations.
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserHelper userHelper;

    /**
     * Get a user by their identifier (userId).
     *
     * @param userAuthenticated The authenticated user
     * @param userId The identifier of the user to retrieve.
     * @return The user corresponding to the identifier.
     */
    @GetMapping("/{userId}")
    ResponseEntity<User> getUser(@AuthenticationPrincipal User userAuthenticated, @PathVariable String userId){
        if(!userHelper.isOwnerOrAdmin(userAuthenticated, userId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Update an existing user's information with new data.
     *
     * @param userAuthenticated The authenticated user
     * @param newUser The new user information.
     * @return The updated user.
     */
    @PutMapping
    ResponseEntity<User> updateUser(@AuthenticationPrincipal User userAuthenticated, @RequestBody User newUser) {
        if(!userHelper.isOwnerOrAdmin(userAuthenticated, newUser.getId()))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Optional<User> userOptional = userService.putUser(newUser);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    /**
     * Delete a user by their identifier (userId).
     *
     * @param userAuthenticated The authenticated user
     * @param userId The identifier of the user to delete.
     */
    @DeleteMapping("/{userId}")
    ResponseEntity<User> deleteUser(@AuthenticationPrincipal User userAuthenticated, @PathVariable String userId) {
        if(!userHelper.isOwnerOrAdmin(userAuthenticated, userId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return userService.deleteUser(userId) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
