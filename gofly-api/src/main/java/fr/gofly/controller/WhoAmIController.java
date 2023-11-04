package fr.gofly.controller;

import fr.gofly.dto.UserDto;
import fr.gofly.mapper.UserToUserDto;
import fr.gofly.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WhoAmIController {
    private final UserToUserDto userMapper;

    @GetMapping("/api/whoami")
    public ResponseEntity<UserDto> whoAmI(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userMapper.map(user));
    }
}
