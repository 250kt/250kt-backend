package fr.gofly.controller;

import fr.gofly.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WhoAmIController {
    @GetMapping("/api/whoami")
    public ResponseEntity<String> whoAmI(@AuthenticationPrincipal User user) {
        user.setPassword(null);
        return ResponseEntity.ok(user.toString());
    }
}
