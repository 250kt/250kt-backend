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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Register a new user.
     *
     * @param request The registration request.
     * @return AuthenticationResponse with access and refresh tokens.
     */
    public Optional<AuthenticationResponse> register(RegisterRequest request) {
        //Check the email regex (RFC 5322 Official Standard) before check if the user already exist permit to avoid SQL injection
        String emailRegex = "^((?:[A-Za-z0-9!#$%&'*+\\-\\/=?^_`{|}~]|(?<=^|\\.)\"|\"(?=$|\\.|@)|(?<=\".*)[ .](?=.*\")|(?<!\\.)\\.){1,64})(@)((?:[A-Za-z0-9.\\-])*(?:[A-Za-z0-9])\\.(?:[A-Za-z0-9]){2,})$";
        Pattern emailPattern = Pattern.compile(emailRegex);

        if(!emailPattern.matcher(request.getEmail()).matches())
            return Optional.empty();


        String usernameRegex = "^[A-Za-z][A-Za-z0-9_]{2,29}$";
        Pattern usernamePattern = Pattern.compile(usernameRegex);

        if(!usernamePattern.matcher(request.getUsername()).matches())
            return Optional.empty();

        if(request.getPassword().length() == 0)
            return Optional.empty();

        if (userRepository.findByUserEmail(request.getEmail()).isPresent())
            return Optional.empty();

        // Create a new user and encode the password.
        User user = User.builder()
                .userName(request.getUsername())
                .userEmail(request.getEmail())
                .userPassword(passwordEncoder.encode(request.getPassword()))
                .userRoles(Collections.singletonList(Role.BUDDING_PILOT))
                .userEmailConfirmed(false)
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
            user = userRepository.findByUserName(request.getUsername());
        }else{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            user = userRepository.findByUserEmail(request.getEmail());
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
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user.getUserId());
        if(validUserTokens.isEmpty()){
            return;
        }
        // Mark all valid tokens as expired and revoked.
        validUserTokens.forEach(t -> {
            t.setTokenExpired(true);
            t.setTokenRevoked(true);
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
                .tokenHex(jwtToken)
                .tokenType(TokenType.BEARER)
                .tokenExpired(false)
                .tokenRevoked(false)
                .build();
        tokenRepository.save(token);
    }

    /**
     * Refresh the authentication token.
     *
     * @param request  The original HTTP request.
     * @param response The HTTP response to return with a new authentication token.
     * @throws IOException In case of an error during token refresh handling.
     */
    public void refreshToken(HttpServletResponse request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String usernameOrEmail;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }

        // Extract and validate the refresh token.
        refreshToken = authHeader.substring(7); // substring after "Bearer "

        usernameOrEmail = jwtService.extractUsernameOrEmail(refreshToken);

        if(usernameOrEmail != null){
            User user = userRepository.findByUserName(usernameOrEmail)
                    .orElseThrow();

            boolean isTokenValid = tokenRepository.findByTokenHex(refreshToken)
                    .map(t -> !t.isTokenRevoked() && !t.isTokenExpired())
                    .orElse(false);

            if(jwtService.isTokenValid(refreshToken, user) && isTokenValid){
                // Generate a new access token for the user.
                String accessToken = jwtService.generateToken(user);
                // Revoke old tokens, save the new token, and return the updated token response.
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                AuthenticationResponse authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
