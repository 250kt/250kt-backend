package fr.gofly.helper;

import fr.gofly.model.Authority;
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
     * @return true if the user is the owner of the account
     */
    public boolean isOwner(User user, String userId) {
        return Objects.equals(user.getId(), userId);
    }

    /**
     * Check if the user account is owned by the user or admin
     *
     * @param user the {@link User}
     * @param userId the {@link String}
     * @return true if the user is the owner of the account or ADMIN
     */
    public boolean isOwnerOrAdmin(User user, String userId){
        return isOwner(user, userId) || isAdmin(user);
    }

    /**
     * Check if the user is ADMIN
     *
     * @param user the {@link User}
     * @return true if the user is ADMIN
     */
    public boolean isAdmin(User user){
        return user.getAuthorities().contains(Authority.ADMIN);
    }
}
