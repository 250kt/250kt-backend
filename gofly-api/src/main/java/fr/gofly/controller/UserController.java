package fr.gofly.controller;

import fr.gofly.model.User;
import fr.gofly.repository.UserRepository;
import fr.gofly.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fr.gofly.exception.entity.user.UserNotFoundException;

@RestController
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/users")
    public User newUser(@RequestBody User newUser){
        return userService.createUser(newUser);
    }

    @GetMapping("/users/{userId}")
    User one(@PathVariable String userId){
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @PutMapping("/users/{userId}")
    User replaceUser(@RequestBody User newUser, @PathVariable String userId) {
        return userService.putUser(newUser, userId);
    }

    @DeleteMapping("/users/{userId}")
    void deleteUser(@PathVariable String userId) {
        userService.DeleteUser(userId);

        /*try {
            userRepository.deleteByUserId(userId);
            return ResponseEntity.ok("The user has been successfully deleted.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has been encountered when delete the user.");
        }*/
    }
}
