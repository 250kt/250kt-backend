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
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(jwtService.generateToken(any(User.class))).thenReturn("token");
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("refreshToken");

        Optional<AuthenticationResponse> response = authenticationService.register(request);

        assertTrue(response.isPresent());
        assertEquals("token", response.get().getAccessToken());
        assertEquals("refreshToken", response.get().getRefreshToken());
    }

    @Test
    void test_ShouldReturnAuthenticationResponse_WhenAuthenticate_AndWhenEmailIsNull(){
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
    void test_ShouldReturnAuthenticationResponse_WhenAuthenticate_AndWhenEmailNotNull(){
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
