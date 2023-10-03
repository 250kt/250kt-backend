package fr.gofly.service;

import fr.gofly.exception.entity.user.UserAlreadyExistsException;
import fr.gofly.model.User;
import fr.gofly.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * Service class responsible for handling user-related operations.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates a new user.
     *
     * @param newUser The user object to be created.
     * @return The newly created user.
     * @throws UserAlreadyExistsException If a user with the same email address already exists.
     */
    public User createUser(User newUser) {
        if (userRepository.findByUserEmail(newUser.getUserEmail()).isPresent()) {
            throw new UserAlreadyExistsException(newUser.getUserEmail());
        }

        return userRepository.save(newUser);
    }

    /**
     * Updates an existing user with the provided data.
     *
     * @param newUser The updated user data.
     * @param userId  The unique identifier of the user to be updated.
     * @return The updated user.
     * @throws UserAlreadyExistsException If a user with the same email already exists.
     */
    public User putUser(User newUser, String userId){
        //We retrieve the current user to compare if the email is the same in order to avoid error 500 UserAlreadyExistsException
        Optional<User> currentUser = userRepository.findByUserId(userId);
        String currentUserEmail = currentUser.get().getUserEmail();

        //If the email hasn't changed
        if(Objects.equals(newUser.getUserEmail(), currentUserEmail)){
            return userRepository.findByUserId(userId)
                    .map(user -> {
                        user.setUserPassword(newUser.getUserPassword());
                        return userRepository.save(user);
                    })
                    .orElseGet(() -> {
                        newUser.setUserId(userId);
                        return userRepository.save(newUser);
                    });
        }

        //If the email has changed and the email already exist in database -> UserAlreadyExistsException
        if (userRepository.findByUserEmail(newUser.getUserEmail()).isPresent()) {
            throw new UserAlreadyExistsException(newUser.getUserEmail());
        }

        return userRepository.findByUserId(userId)
                .map(user -> {
                    user.setUserEmail(newUser.getUserEmail());
                    user.setUserPassword(newUser.getUserPassword());
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setUserId(userId);
                    return userRepository.save(newUser);
                });
    }

    /**
     * Deletes a user by their unique identifier.
     *
     * @param userId The unique identifier of the user to be deleted.
     */
    @Transactional
    public void DeleteUser(String userId) {
        userRepository.deleteByUserId(userId);
    }
}
