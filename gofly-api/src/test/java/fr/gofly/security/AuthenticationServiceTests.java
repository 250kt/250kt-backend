package fr.gofly.security;

import fr.gofly.model.User;
import fr.gofly.model.token.Token;
import fr.gofly.repository.TokenRepository;
import fr.gofly.repository.UserRepository;
import fr.gofly.security.auth.AuthenticationRequest;
import fr.gofly.security.auth.AuthenticationResponse;
import fr.gofly.security.auth.AuthenticationService;
import fr.gofly.security.auth.RegisterRequest;
import fr.gofly.security.config.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTests {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    void testRegister_ShouldReturnAuthenticationResponse(){
        RegisterRequest request = RegisterRequest.builder()
                .username("test")
                .email("test@250kt.com")
                .password("testPassword")
                .build();

        when(tokenRepository.save(any(Token.class))).thenReturn(new Token());
        when(passwordEncoder.encode(anyString())).thenReturn(new String("passwordEncoded"));
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(jwtService.generateToken(any(User.class))).thenReturn("token");
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("refreshToken");

        Optional<AuthenticationResponse> response = authenticationService.register(request);

        assertTrue(response.isPresent());
        assertEquals("token", response.get().getAccessToken());
        assertEquals("refreshToken", response.get().getRefreshToken());
    }

    @Test
    void testRegister_ShouldReturnOptionalEmpty_WhenEmailInvalid(){
        RegisterRequest request = RegisterRequest.builder()
                .username("test")
                .email("test@.com")
                .password("testPassword")
                .build();

        assertEquals(Optional.empty(), authenticationService.register(request));
    }

    @Test
    void testRegister_ShouldReturnOptionalEmpty_WhenUsernameInvalid(){
        RegisterRequest request = RegisterRequest.builder()
                .username("T€st")
                .email("test@250kt.com")
                .password("testPassword")
                .build();

        assertEquals(Optional.empty(), authenticationService.register(request));
    }

    @Test
    void testRegister_ShouldReturnOptionalEmpty_WhenPasswordEmpty(){
        RegisterRequest request = RegisterRequest.builder()
                .username("Test")
                .email("test@250kt.com")
                .password("")
                .build();

        assertEquals(Optional.empty(), authenticationService.register(request));
    }

    @Test
    void testRegister_ShouldReturnOptionalEmpty_WhenAlreadyExist(){
        RegisterRequest request = RegisterRequest.builder()
                .username("Test")
                .email("test@250kt.com")
                .password("testPassword")
                .build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        assertEquals(Optional.empty(), authenticationService.register(request));
    }

    @Test
    void testAuthenticate_ShouldReturnAuthenticationResponse_AndWhenEmailIsNull(){
        AuthenticationRequest request = AuthenticationRequest.builder()
                .username("test")
                .password("testPassword")
                .build();

        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(new User()));
        when(jwtService.generateToken(any(User.class))).thenReturn("token");
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("refreshToken");

        Optional<AuthenticationResponse> response = authenticationService.authenticate(request);

        assertTrue(response.isPresent());
        assertEquals("token", response.get().getAccessToken());
        assertEquals("refreshToken", response.get().getRefreshToken());
    }

    @Test
    void testAuthenticate_ShouldReturnAuthenticationResponse_AndWhenEmailNotNull(){
        AuthenticationRequest request = AuthenticationRequest.builder()
                .email("test@250kt.com")
                .password("testPassword")
                .build();

        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new User()));
        when(jwtService.generateToken(any(User.class))).thenReturn("token");
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("refreshToken");

        Optional<AuthenticationResponse> response = authenticationService.authenticate(request);

        assertTrue(response.isPresent());
        assertEquals("token", response.get().getAccessToken());
        assertEquals("refreshToken", response.get().getRefreshToken());
    }
}
