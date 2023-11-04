package fr.gofly.controller;

import fr.gofly.dto.UserDto;
import fr.gofly.helper.UserHelper;
import fr.gofly.model.User;
import fr.gofly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

/**
 * Controller for user-related operations.
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
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
    ResponseEntity<UserDto> getUser(@AuthenticationPrincipal User userAuthenticated, @PathVariable String userId){
        if(!userHelper.isOwnerOrAdmin(userAuthenticated, userId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Optional<UserDto> userOptional = userService.getUserById(userId);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Update an existing user's information with new data.
     *
     * @param userAuthenticated The authenticated user
     * @param newUser The new user information.
     * @return The updated user.
     */
    @PutMapping()
    ResponseEntity<UserDto> updateUser(@AuthenticationPrincipal User userAuthenticated, @RequestBody User newUser) {
        if(!userHelper.isOwnerOrAdmin(userAuthenticated, newUser.getId()))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Optional<UserDto> userDtoOptional = userService.updateUser(newUser);
        return userDtoOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    /**
     * Delete a user by their identifier (userId).
     *
     * @param userAuthenticated The authenticated user
     * @param userId The identifier of the user to delete.
     */
    @DeleteMapping("/{userId}")
    ResponseEntity<HttpStatus> deleteUser(@AuthenticationPrincipal User userAuthenticated, @PathVariable String userId) {
        if(!userHelper.isOwnerOrAdmin(userAuthenticated, userId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return userService.deleteUser(userId) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Get all users
     *
     * @return The all users.
     */
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Set<UserDto>> getAllUsers(){
        Optional<Set<UserDto>> userDtoOptional = userService.getAllUsers();
        return userDtoOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
