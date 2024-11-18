package fr.gofly.service;

import fr.gofly.dto.UserDto;
import fr.gofly.mapper.UserToUserDto;
import fr.gofly.model.Authority;
import fr.gofly.model.User;
import fr.gofly.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserToUserDto userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    private List<Authority> authorities = new ArrayList<>();

    @Mock
    private JavaMailSender javaMailSender;

    private User user;
    private String fromEmail;

    @BeforeEach
    void setUp(){
        authorities.add(Authority.BUDDING_PILOT);
        user = new User();
        user.setEmail("test@example.com");
        user.setVerificationCode("verificationCode");
        fromEmail = "fromEmail";
    }

    @Test
    void test_ShouldReturnOptionalEmpty_WhenPutUser(){

        when(userRepository.findById((String) any())).thenReturn(Optional.empty());

        assertEquals(Optional.empty(), userService.updateUser(user));
    }

    @Test
    void testUpdateUser_ShouldReturnOptionalUser_WhenEmailDoesntChanged(){
        when(userRepository.findById((String) any())).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.map(any(User.class))).thenReturn(new UserDto());

        assertEquals(Optional.of(userMapper.map(user)), userService.updateUser(user));
    }

    @Test
    void testUpdateUser_ShouldReturnOptionalUser_WhenEmailChangedAlreadyExist(){
        user.setId("id");

        when(userRepository.findById((String) any())).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(new User()));

        assertEquals(Optional.empty(), userService.updateUser(user));
    }

    @Test
    void test_ShouldReturnUser_WhenPutUser_AndEmailDoesNotExistInDatabase(){

        when(userRepository.findById((String) any())).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.map(any(User.class))).thenReturn(new UserDto());

        assertEquals(Optional.of(userMapper.map(user)), userService.updateUser(user));
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
        if(Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", newPassword)){
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
