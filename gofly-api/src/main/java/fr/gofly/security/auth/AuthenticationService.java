package fr.gofly.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.gofly.security.config.JwtService;
import fr.gofly.model.Role;
import fr.gofly.model.User;
import fr.gofly.model.token.Token;
import fr.gofly.model.token.TokenType;
import fr.gofly.repository.TokenRepository;
import fr.gofly.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     *
     * @param request the {@link RegisterRequest}
     * @return {@link AuthenticationResponse}
     */
    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .userName(request.getUsername())
                .userEmail(request.getEmail())
                .userPassword(passwordEncoder.encode(request.getPassword()))
                .userRole(Role.BUDDING_PILOT)
                .userEmailConfirmed(false)
                .build();

        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);

        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     *
     * @param request the {@link AuthenticationRequest}
     * @return {@link AuthenticationResponse}
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user;

        if(request.getEmail() == null){
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            user = userRepository.findByUserName(request.getUsername())
                    .orElseThrow();
        }else{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            user = userRepository.findByUserEmail(request.getEmail())
                    .orElseThrow();
        }

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        saveUserToken(user, refreshToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     *
     * @param user {@link User}
     */
    private void revokeAllUserTokens(User user){
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getUserId());
        if(validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(t -> {
            t.setTokenExpired(true);
            t.setTokenRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    /**
     *
     * @param savedUser {@link User}
     * @param jwtToken the jwt token
     */
    private void saveUserToken(User savedUser, String jwtToken) {
        var token = Token.builder()
                .user(savedUser)
                .tokenHex(jwtToken)
                .tokenType(TokenType.BEARER)
                .tokenExpired(false)
                .tokenRevoked(false)
                .build();
        tokenRepository.save(token);
    }

    /**
     *
     * @param request {@link HttpServletResponse}
     * @param response {@link HttpServletResponse}
     * @throws IOException
     */
    public void refreshToken(HttpServletResponse request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String usernameOrEmail;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }

        refreshToken = authHeader.substring(7); // substring after "Bearer "

        usernameOrEmail = jwtService.extractUsernameOrEmail(refreshToken);

        if(usernameOrEmail != null){
            var user = this.userRepository.findByUserName(usernameOrEmail)
                    .orElseThrow();

            var isTokenValid = tokenRepository.findByTokenHex(refreshToken)
                    .map(t -> !t.isTokenRevoked() && !t.isTokenExpired())
                    .orElse(false);

            if(jwtService.isTokenValid(refreshToken, user) && isTokenValid){
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
