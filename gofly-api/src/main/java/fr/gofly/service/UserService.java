package fr.gofly.service;

import fr.gofly.exception.entity.user.UserAlreadyExistsException;
import fr.gofly.model.User;
import fr.gofly.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User newUser) {
        if (userRepository.findByUserEmail(newUser.getUserEmail()).isPresent()) {
            throw new UserAlreadyExistsException(newUser.getUserEmail());
        }

        return userRepository.save(newUser);
    }

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

    @Transactional
    public void DeleteUser(String userId) {
        userRepository.deleteByUserId(userId);
    }
}
