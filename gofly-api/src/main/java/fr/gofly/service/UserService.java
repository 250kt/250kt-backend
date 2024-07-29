package fr.gofly.service;

import fr.gofly.dto.AirfieldDto;
import fr.gofly.dto.UserDto;
import fr.gofly.mapper.AirfieldToAirfieldDto;
import fr.gofly.mapper.UserToUserDto;
import fr.gofly.model.User;
import fr.gofly.model.airfield.Airfield;
import fr.gofly.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private final AirfieldToAirfieldDto airfieldMapper;

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

    public void updateLastConnection(String userId){
        userRepository.findById(userId).ifPresent(user -> {
            user.setLastConnection(LocalDateTime.now());
            userRepository.save(user);
        });
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
        return userRepository.findById(userId).map(userMapper::map);

    }

    public Optional<Set<UserDto>> getAllUsers(){
        return Optional.of(userRepository.findAll().stream().map(userMapper::map).collect(Collectors.toSet()));
    }

    public Optional<AirfieldDto> getFavoritedAirfield(User user){
        return userRepository.findFavoriteAirfield(user).map(airfieldMapper::map);
    }

    public boolean updateFavoriteAirfield(User user, Airfield airfield){
        if(user.getFavoriteAirfield() != null && user.getFavoriteAirfield().getId().equals(airfield.getId())){
            user.setFavoriteAirfield(null);
        }else{
            user.setFavoriteAirfield(airfield);
        }
        userRepository.save(user);
        return true;
    }
}
