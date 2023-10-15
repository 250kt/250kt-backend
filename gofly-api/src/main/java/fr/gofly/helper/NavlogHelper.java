package fr.gofly.helper;

import fr.gofly.model.Navlog;
import fr.gofly.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class NavlogHelper {

    public boolean isNavlogOwnedByUser(Navlog navlog, User user){
        return Objects.equals(navlog.getUser().getId(), user.getId());
    }

    public boolean isMissingMandatoryFields(Navlog navlog){
        int steps = navlog.getSteps().size();

        return navlog.getAircraft() != null &&
               navlog.getUser() != null &&
               steps >= 2 ;
    }
}
