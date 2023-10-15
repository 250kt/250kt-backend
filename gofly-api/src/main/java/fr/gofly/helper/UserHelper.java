package fr.gofly.helper;

import fr.gofly.model.User;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserHelper {
    /**
     * Check if the user account is owned by the user
     *
     * @param user the {@link User}
     * @param userId the {@link String}
     * @return true if the aircraft owned by the user, otherwise false
     */
    public boolean isUserAccountOwnedByUser(User user, String userId) {
        return Objects.equals(user.getId(), user.getId());
    }
}
