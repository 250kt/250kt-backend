package fr.gofly.helper;

import fr.gofly.model.Aircraft;
import fr.gofly.model.User;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AircraftHelper {

    /**
     * Check if aircraft has mandatory field without value
     *
     * @param aircraft the {@link Aircraft}
     * @return true if a mandatory field missing, otherwise false
     */
    public boolean isMissingMandatoryField(Aircraft aircraft) {
        return aircraft.getBaseFactor() == null ||
                aircraft.getBaseFactor() < 0.0 ||
                aircraft.getConsumption() == null ||
                aircraft.getNonPumpableFuel() == null ||
                aircraft.getTankCapacity() == null ||
                aircraft.getTrueAirSpeed() == null;
    }

    /**
     * Check if the aircraft are owned by the user
     *
     * @param aircraft the {@link Aircraft}
     * @param user     the {@link User}
     * @return true if the aircraft owned by the user, otherwise false
     */
    public boolean isAircraftOwnedByUser(Aircraft aircraft, User user) {
        return Objects.equals(aircraft.getUser().getId(), user.getId());
    }
}
