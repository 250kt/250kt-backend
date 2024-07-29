package fr.gofly.mapper;

import fr.gofly.dto.UserDto;
import fr.gofly.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDto {

    public UserDto map(User user){
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .authorities(user.getAuthorities())
                .isEmailConfirmed(user.getIsEmailConfirmed())
                .username(user.getUsername())
                .favoriteAirfield(user.getFavoriteAirfield())
                .avatar(user.getAvatar().getAvatar())
                .build();
    }
}
