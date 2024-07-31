package fr.gofly.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String id;
    private String username;
    private String email;
    private Boolean isEmailConfirmed;
    private Collection<? extends GrantedAuthority> authorities;
    private AirfieldShortDto favoriteAirfield;
    private String avatar;
}
