package fr.gofly.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.gofly.model.Authority;
import fr.gofly.model.PilotAvatar;
import fr.gofly.security.config.JwtService;
import fr.gofly.model.User;
import fr.gofly.model.token.Token;
import fr.gofly.model.token.TokenType;
import fr.gofly.repository.TokenRepository;
import fr.gofly.repository.UserRepository;
import fr.gofly.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    //Check the email regex (RFC 5322 Official Standard) before check if the user already exist permit to avoid SQL injection
    private final Pattern emailPattern = Pattern.compile("^((?:[A-Za-z0-9!#$%&'*+\\-\\/=?^_`{|}~]|(?<=^|\\.)\"|\"(?=$|\\.|@)|(?<=\".*)[ .](?=.*\")|(?<!\\.)\\.){1,64})(@)((?:[A-Za-z0-9.\\-])*(?:[A-Za-z0-9])\\.(?:[A-Za-z0-9]){2,})$");
    private final Pattern usernamePattern = Pattern.compile("^[A-Za-z][A-Za-z0-9_]{2,29}$");

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 16;
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Register a new user.
     *
     * @param request The registration request.
     * @return AuthenticationResponse with access and refresh tokens.
     */
    public Optional<AuthenticationResponse> register(RegisterRequest request) {

        if(!emailPattern.matcher(request.getEmail()).matches())
            return Optional.empty();

        if(!usernamePattern.matcher(request.getUsername()).matches())
            return Optional.empty();

        if(request.getPassword().isEmpty())
            return Optional.empty();

        if (userRepository.findByEmail(request.getEmail()).isPresent())
            return Optional.empty();

        String verificationCode = generateVerificationCode();

        // Create a new user and encode the password.
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .authorities(Collections.singletonList(Authority.BUDDING_PILOT))
                .isEmailConfirmed(false)
                .lastConnection(LocalDateTime.now())
                .favoriteAirfield(request.getFavoriteAirfield())
                .verificationCode(verificationCode)
                .avatar(PilotAvatar.PILOT_MAN)
                .build();

        User savedUser = userRepository.save(user);

        // Generate JWT and refresh tokens for the user.
        String jwtToken = jwtService.generateToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(savedUser);

        // Save the tokens and return the response.
        saveUserToken(savedUser, jwtToken);

        return Optional.of(AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build());
    }

    /**
     * Authenticate a user.
     *
     * @param request The authentication request.
     * @return AuthenticationResponse with access and refresh tokens.
     */
    public Optional<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        Optional<User> user;

        // Authenticate the user based on provided credentials.
        if(request.getEmail() == null){
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            user = userRepository.findByUsername(request.getUsername());
        }else{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            user = userRepository.findByEmail(request.getEmail());
        }

        if(user.isEmpty())
            return Optional.empty();

        // Generate new JWT and refresh tokens for the user.
        String jwtToken = jwtService.generateToken(user.get());
        String refreshToken = jwtService.generateRefreshToken(user.get());

        // Revoke old tokens and save the new tokens.
        revokeAllUserTokens(user.get());
        saveUserToken(user.get(), jwtToken);

        // Save the tokens and return the response.
        saveUserToken(user.get(), refreshToken);

        // Update the last connection time.
        userService.updateLastConnection(user.get().getId());

        return Optional.of(AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build());
    }

    /**
     * Revoke all valid user tokens.
     *
     * @param user The user for whom tokens need to be revoked.
     */
    private void revokeAllUserTokens(User user){
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if(validUserTokens.isEmpty()){
            return;
        }
        // Mark all valid tokens as expired and revoked.
        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    /**
     * Save a token for a user.
     *
     * @param savedUser The user for whom the token is saved.
     * @param jwtToken The JWT token.
     */
    private void saveUserToken(User savedUser, String jwtToken) {
        Token token = Token.builder()
                .user(savedUser)
                .hex(jwtToken)
                .type(TokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.save(token);
    }

    /**
     * Refresh the authentication token.
     *
     * @param request  The original HTTP request.
     */
    public Optional<AuthenticationResponse> refreshToken(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String usernameOrEmail;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return Optional.empty();
        }
        // Extract and validate the refresh token.
        refreshToken = authHeader.substring(7); // substring after "Bearer "
        usernameOrEmail = jwtService.extractUsernameOrEmail(refreshToken);

        if(usernameOrEmail != null){
            Optional<User> userOptional = userRepository.findByUsername(usernameOrEmail);
            if(userOptional.isEmpty()){
                return Optional.empty();
            }
            User user = userOptional.get();

            boolean isTokenValid = tokenRepository.findByHex(refreshToken)
                    .map(t -> !t.isRevoked() && !t.isExpired())
                    .orElse(false);

            if(jwtService.isTokenValid(refreshToken, user) && isTokenValid){
                // Generate a new access token for the user.
                String accessToken = jwtService.generateToken(user);
                String newRefreshToken = jwtService.generateRefreshToken(user);
                // Revoke old tokens, save the new token, and return the updated token response.
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                saveUserToken(user, newRefreshToken);
                AuthenticationResponse authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(newRefreshToken)
                        .build();

                return Optional.of(authResponse);
            }
        }
        return Optional.empty();
    }

    private String generateVerificationCode() {
        return RANDOM.ints(CODE_LENGTH, 0, CHARACTERS.length())
                .mapToObj(i -> String.valueOf(CHARACTERS.charAt(i)))
                .collect(Collectors.joining());
    }
}
