package fr.gofly.helper;

import fr.gofly.model.Navlog;
import fr.gofly.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Helper class for validating and processing navigation logs (Navlogs).
 */
@Component
@RequiredArgsConstructor
public class NavlogHelper {

    /**
     * Check if a navigation log (Navlog) is owned by a specific user.
     *
     * @param navlog The Navlog to check.
     * @param user The user to compare ownership.
     * @return True if the Navlog is owned by the user, false otherwise.
     */
    public boolean isNavlogOwnedByUser(Navlog navlog, User user){
        return Objects.equals(navlog.getUser().getId(), user.getId());
    }

    /**
     * Check if a navigation log (Navlog) has all mandatory fields filled.
     *
     * @param navlog The Navlog to check.
     * @return True if the Navlog contains all mandatory fields, false otherwise.
     */
    public boolean isMissingMandatoryFields(Navlog navlog){
        int steps = navlog.getSteps().size();

        return navlog.getAircraft() != null &&
               navlog.getUser() != null &&
               steps >= 2 ;
    }
}
