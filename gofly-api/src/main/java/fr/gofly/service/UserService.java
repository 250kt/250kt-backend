package fr.gofly.service;

import fr.gofly.dto.AirfieldShortDto;
import fr.gofly.dto.UserDto;
import fr.gofly.mapper.AirfieldToAirfieldShortDto;
import fr.gofly.mapper.UserToUserDto;
import fr.gofly.model.User;
import fr.gofly.model.airfield.Airfield;
import fr.gofly.repository.AircraftRepository;
import fr.gofly.repository.TokenRepository;
import fr.gofly.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class UserService{

    private final UserRepository userRepository;
    private final UserToUserDto userMapper;
    private final AirfieldToAirfieldShortDto airfieldShortMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final AircraftRepository aircraftRepository;
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username:null}")
    private String fromEmail;

    @Value("${app.url:null}")
    private String appUrl;

    @Autowired
    public UserService(UserRepository userRepository, UserToUserDto userMapper, AirfieldToAirfieldShortDto airfieldShortMapper, PasswordEncoder passwordEncoder, TokenRepository tokenRepository, AircraftRepository aircraftRepository, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.airfieldShortMapper = airfieldShortMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.aircraftRepository = aircraftRepository;
        this.javaMailSender = javaMailSender;
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

            //If the user with the email passed exist and is not the same as the user given
            if(userFindByEmail.isPresent() && !Objects.equals(userFindByEmail.get().getId(), user.getId())){
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
            tokenRepository.deleteAllByUserId(userId);
            aircraftRepository.deleteAllByUserId(userId);
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

    public Optional<AirfieldShortDto> getFavoritedAirfield(User user){
        return userRepository.findFavoriteAirfield(user).map(airfieldShortMapper::map);
    }

    public boolean updateFavoriteAirfield(User user, Airfield airfield){
        user.setFavoriteAirfield(airfield);
        userRepository.save(user);
        return true;
    }

    public boolean sendConfirmationEmail(User user){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom(fromEmail);
        mailMessage.setSubject("Confirm your adresse email");
        mailMessage.setText("Click on the link to confirm your email: " + appUrl + "confirm-email?code=" + user.getVerificationCode());
        javaMailSender.send(mailMessage);
        return true;
    }

    public boolean confirmEmail(String code){
        Optional<User> userOptional = userRepository.findByVerificationCodeAndIsEmailConfirmedFalse(code);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setIsEmailConfirmed(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean changePassword(User user, String newPassword){
        if(Pattern.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", newPassword)){
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setFrom(fromEmail);
            mailMessage.setSubject("Password changed");
            mailMessage.setText("Your password has been changed.");
            javaMailSender.send(mailMessage);
            return true;
        }
        return false;
    }
}
