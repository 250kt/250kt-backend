package fr.gofly.mapper;

import fr.gofly.dto.UserDto;
import fr.gofly.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserToUserDto {

    private final AirfieldToAirfieldShortDto airfieldShortMapper;

    public UserDto map(User user){
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .authorities(user.getAuthorities())
                .isEmailConfirmed(user.getIsEmailConfirmed())
                .username(user.getUsername())
                .favoriteAirfield(airfieldShortMapper.map(user.getFavoriteAirfield()))
                .avatar(user.getAvatar().getAvatar())
                .build();
    }
}
