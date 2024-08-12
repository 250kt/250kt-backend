package fr.gofly.helper;

import fr.gofly.model.flight.Flight;
import fr.gofly.model.flight.FlightMetrics;
import fr.gofly.model.flight.Step;
import fr.gofly.repository.StepRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FlightHelper {

    private final StepRepository stepRepository;

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


    public List<Step> computeStepsMetrics(List<Step> steps, Flight currentFlight) {
        for(int i = 0; i < steps.size()-1; i++) {
            Step s = steps.get(i);
            if (s.getOrder() != steps.size()-1){
                FlightMetrics metrics = calculateMetricsBetweenTwoPoints(s.getAirfield().getLatitude(), s.getAirfield().getLongitude(), steps.get(i+1).getAirfield().getLatitude(), steps.get(i+1).getAirfield().getLongitude());
                s.setDistance(metrics.distance());
                s.setCap(metrics.direction());
                s.setDuration(calculateDuration(s.getDistance(), currentFlight.getAircraft().getBaseFactor()));
            }else{
                s.setDistance(0.0);
                s.setCap(0);
                s.setDuration(0);
            }
        }
        return steps;
    }

    public Flight computeTotalMetrics(Flight currentFlight) {
        List<Step> steps = stepRepository.findAllByFlightOrderByOrder(currentFlight);
        double totalDistance = steps.stream().mapToDouble(Step::getDistance).sum();
        totalDistance = Math.round(totalDistance);
        int totalDuration = steps.stream().mapToInt(Step::getDuration).sum();
        currentFlight.setDistance(totalDistance);
        currentFlight.setDuration(totalDuration);
        return currentFlight;
    }

}
