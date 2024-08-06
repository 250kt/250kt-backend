package fr.gofly.helper;

import fr.gofly.model.Flight;
import fr.gofly.model.FlightMetrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FlightHelper {

    private static final Logger log = LoggerFactory.getLogger(FlightHelper.class);

    public boolean isMissingMandatoryField(Flight flight) {
        return flight.getCreatedAt() == null || flight.getAircraft() == null;
    }

    public FlightMetrics calculateMetricsBetweenTwoPoints(float lat1, float lon1, float lat2, float lon2) {
        // Convert latitude and longitude from degrees to radians
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Calculate the differences
        double latDiff = lat2Rad - lat1Rad;
        double lonDiff = lon2Rad - lon1Rad;

        // Haversine formula to calculate the distance
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = 6371 * c; // Radius of the Earth in kilometers

        // Calculate the direction
        double y = Math.sin(lonDiff) * Math.cos(lat2Rad);
        double x = Math.cos(lat1Rad) * Math.sin(lat2Rad) -
                Math.sin(lat1Rad) * Math.cos(lat2Rad) * Math.cos(lonDiff);
        double directionRad = Math.atan2(y, x);
        double directionDeg = Math.toDegrees(directionRad);

        // Normalize the direction to a range of 0 to 360 degrees
        if (directionDeg < 0) {
            directionDeg += 360;
        }

        // Convert distance to nautical miles
        distance = Math.round((distance / 1.852) * 100.0) / 100.0;

        return new FlightMetrics(distance, (int) directionDeg);
    }

    public int calculateDuration(double distance, Double baseFactor) {
        return (int) (distance * baseFactor);
    }
}
