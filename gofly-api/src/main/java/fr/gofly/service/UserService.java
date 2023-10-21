package fr.gofly.service;

import fr.gofly.dto.UserDto;
import fr.gofly.mapper.UserToUserDto;
import fr.gofly.model.User;
import fr.gofly.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Service class responsible for handling user-related operations.
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserToUserDto userMapper;

    /**
     * Creates a new user.
     *
     * @param newUser The user object to be created.
     * @return The newly created user.
     */
    public Optional<User> createUser(User newUser) {
        //Check the email regex (RFC 5322 Official Standard) before check if the user already exist permit to avoid SQL injection
        String emailRegex = "^((?:[A-Za-z0-9!#$%&'*+\\-\\/=?^_`{|}~]|(?<=^|\\.)\"|\"(?=$|\\.|@)|(?<=\".*)[ .](?=.*\")|(?<!\\.)\\.){1,64})(@)((?:[A-Za-z0-9.\\-])*(?:[A-Za-z0-9])\\.(?:[A-Za-z0-9]){2,})$";
        Pattern emailPattern = Pattern.compile(emailRegex);

        if(!emailPattern.matcher(newUser.getEmail()).matches())
            return Optional.empty();


        String usernameRegex = "^[A-Za-z][A-Za-z0-9_]{2,29}$";
        Pattern usernamePattern = Pattern.compile(usernameRegex);

        if(!usernamePattern.matcher(newUser.getUsername()).matches())
            return Optional.empty();

        if(newUser.getPassword().length() == 0)
            return Optional.empty();

        if (userRepository.findByEmail(newUser.getEmail()).isPresent())
            return Optional.empty();

        return Optional.of(userRepository.save(newUser));
    }

    /**
     * Updates an existing user with the provided data.
     *
     * @param user The updated user data.
     * @return The updated user.
     */
    public Optional<UserDto> updateUser(User user){
        //We retrieve the current user to compare if the email is the same in order to avoid error 500 UserAlreadyExistsException
        Optional<User> currentUserDbOptional = userRepository.findById(user.getId());

        //If user exist in database
        if(currentUserDbOptional.isPresent()){
            Optional<User> userFindByEmail = userRepository.findByEmail(user.getEmail());

            //If the user with the email passed exist
            if(userFindByEmail.isPresent()){
                //If the userFindByEmail is not the same as the user given
                if(!Objects.equals(userFindByEmail.get().getId(), user.getId()))
                    return Optional.empty();
            }
            return Optional.of(userMapper.map(userRepository.save(user)));
        }
        return Optional.empty();
    }

    /**
     * Deletes a user by their unique identifier.
     *
     * @param userId that will be deleted.
     */
    @Transactional
    public boolean deleteUser(String userId) {
        try{
            userRepository.deleteById(userId);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Optional<UserDto> getUserById(String userId){
        Optional<UserDto> userDto = userRepository.findById(userId).map(userMapper::map);

        if(userDto.isEmpty())
            return null;

        return userDto;
    }

    public Optional<Set<UserDto>> getAllUsers(){
        return Optional.of(userRepository.findAll().stream().map(userMapper::map).collect(Collectors.toSet()));
    }
}
