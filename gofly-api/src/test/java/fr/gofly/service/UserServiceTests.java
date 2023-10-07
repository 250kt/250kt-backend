package fr.gofly.service;

import fr.gofly.exception.entity.user.UserAlreadyExistsException;
import fr.gofly.model.User;
import fr.gofly.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void test_ShouldReturnAnUser_WhenCreateUser(){
        User user = User.builder().userId("id").build();
        when(userRepository.findByUserEmail(any())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        assertEquals(user, userService.createUser(user));
    }

    @Test
    void test_ShouldThrow_UserAlreadyExistsException_WhenCreateUser(){
        User user = User.builder().userId("id").build();
        when(userRepository.findByUserEmail(any())).thenReturn(Optional.of(user));
        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(user));
    }

    @Test
    void test_ShouldReturnOptionalEmpty_WhenPutUser(){
        User user = User.builder()
                .userId("id")
                .build();
        when(userRepository.findByUserId(any())).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), userService.putUser(user));
    }

    @Test
    void test_ShouldReturnUser_WhenPutUser(){
        User user = User.builder()
                .userId("id")
                .userEmail("test@250kt.com")
                .build();
        when(userRepository.findByUserId(any())).thenReturn(Optional.of(user));
        when(userRepository.findByUserEmail(any())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        assertEquals(Optional.of(user), userService.putUser(user));
    }

    @Test
    void test_ShouldThrow_UserAlreadyExistsException_WhenPutUser(){
        User user = User.builder()
                .userId("id")
                .userEmail("test@250kt.com")
                .build();
        when(userRepository.findByUserId(any())).thenReturn(Optional.of(user));
        when(userRepository.findByUserEmail(any())).thenReturn(Optional.of(new User()));
        assertThrows(UserAlreadyExistsException.class, () -> userService.putUser(user));
    }

    @Test
    void test_ShouldReturnUser_WhenPutUser_AndEmailDoesNotExistInDatabase(){
        User user = User.builder()
                .userId("id")
                .userEmail("test@250kt.com")
                .build();
        when(userRepository.findByUserId(any())).thenReturn(Optional.of(user));
        when(userRepository.findByUserEmail(any())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        assertEquals(Optional.of(user), userService.putUser(user));
    }
}
