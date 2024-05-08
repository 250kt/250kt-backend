package fr.gofly.security.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticateController {
    private final AuthenticationService authenticationService;

    /**
     * Endpoint for user registration.
     *
     * @param request The registration information provided by the user.
     * @return An HTTP response containing the registration operation result.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        Optional<AuthenticationResponse> responseEntity = authenticationService.register(request);
        return responseEntity.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    /**
     * Endpoint for user authentication.
     *
     * @param request The authentication information provided by the user.
     * @return An HTTP response containing the authentication operation result.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        Optional<AuthenticationResponse> responseEntity = authenticationService.authenticate(request);
        return responseEntity.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint for refreshing the authentication token.
     *
     * @param request The original HTTP request.
     * @throws IOException In case of an error during token refresh handling.
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            HttpServletRequest request
    ) throws IOException {
        Optional<AuthenticationResponse> authResponse = authenticationService.refreshToken(request);
        return authResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
