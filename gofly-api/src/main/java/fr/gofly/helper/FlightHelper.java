package fr.gofly.helper;

import fr.gofly.model.Flight;
import org.springframework.stereotype.Component;

@Component
public class FlightHelper {

    public boolean isMissingMandatoryField(Flight flight) {
        return flight.getCreatedAt() == null || flight.getAircraft() == null;
    }

}
