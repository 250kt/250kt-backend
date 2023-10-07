package fr.gofly.service;

import fr.gofly.exception.entity.user.UserAlreadyExistsException;
import fr.gofly.model.User;
import fr.gofly.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * Service class responsible for handling user-related operations.
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    
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
     * @param user The updated user data.
     * @return The updated user.
     * @throws UserAlreadyExistsException If a user with the same email already exists.
     */
    public Optional<User> putUser(User user){
        //We retrieve the current user to compare if the email is the same in order to avoid error 500 UserAlreadyExistsException
        Optional<User> currentUserDbOptional = userRepository.findByUserId(user.getUserId());

        //If user exist in database
        if(currentUserDbOptional.isPresent()){
            Optional<User> userFindByEmail = userRepository.findByUserEmail(user.getUserEmail());

            //If the user with the email passed exist
            if(userFindByEmail.isPresent()){
                //If the userFindByEmail is the same as the user given
                if(Objects.equals(userFindByEmail.get().getUserId(), user.getUserId())){
                    return Optional.of(userRepository.save(user));
                }else{
                    throw new UserAlreadyExistsException(user.getUserEmail());
                }
            }
            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();

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
