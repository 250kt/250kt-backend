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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserToUserDto userMapper;

    private List<Authority> authorities = new ArrayList<>();

    @BeforeEach
    void setUp(){
        authorities.add(Authority.BUDDING_PILOT);
    }

    @Test
    void test_ShouldReturnAnUser_WhenCreateUser(){
        User user = User.builder()
                .id("id")
                .email("test@250kt.com")
                .username("test")
                .password("testPassword")
                .build();

        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        assertEquals(Optional.of(user), userService.createUser(user));
    }

    @Test
    void testCreateUser_ShouldReturnOptionalEmpty_WhenCreateUser(){
        User user = User.builder()
                .id("id")
                .email("test@250kt.com")
                .username("test")
                .password("testPassword")
                .build();

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        assertEquals(Optional.empty(), userService.createUser(user));
    }

    @Test
    void test_ShouldReturnOptionalEmpty_WhenPutUser(){
        User user = User.builder()
                .id("id")
                .build();

        when(userRepository.findById((String) any())).thenReturn(Optional.empty());

        assertEquals(Optional.empty(), userService.updateUser(user));
    }

    @Test
    void testUpdateUser_ShouldReturnOptionalUser_WhenEmailDoesntChanged(){
        User user = User.builder()
                .id("id")
                .email("test@250kt.com")
                .authorities(authorities)
                .build();

        when(userRepository.findById((String) any())).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.map(any(User.class))).thenReturn(new UserDto());

        assertEquals(Optional.of(userMapper.map(user)), userService.updateUser(user));
    }

    @Test
    void testUpdateUser_ShouldReturnOptionalUser_WhenEmailChangedAlreadyExist(){
        User user = User.builder()
                .id("idTest")
                .email("test@250kt.com")
                .build();

        when(userRepository.findById((String) any())).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(new User()));

        assertEquals(Optional.empty(), userService.updateUser(user));
    }

    @Test
    void test_ShouldReturnUser_WhenPutUser_AndEmailDoesNotExistInDatabase(){
        User user = User.builder()
                .id("id")
                .email("test@250kt.com")
                .authorities(authorities)
                .build();

        when(userRepository.findById((String) any())).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.map(any(User.class))).thenReturn(new UserDto());

        assertEquals(Optional.of(userMapper.map(user)), userService.updateUser(user));
    }
}
