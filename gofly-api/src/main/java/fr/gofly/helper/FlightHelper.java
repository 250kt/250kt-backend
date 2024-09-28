package fr.gofly.helper;

import fr.gofly.model.Aircraft;
import fr.gofly.model.User;
import fr.gofly.model.airfield.Airfield;
import fr.gofly.model.flight.*;
import fr.gofly.repository.StepRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FlightHelper {

    private final StepRepository stepRepository;

    public StepMetrics calculateMetricsBetweenTwoPoints(float lat1, float lon1, float lat2, float lon2, double baseFactor) {
        // Convert latitude and longitude from degrees to radians
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Calculate the differences
        double latDiff = lat2Rad - lat1Rad;
        double lonDiff = lon2Rad - lon1Rad;

        double distance = calculateDistance(latDiff, lonDiff, lat1Rad, lat2Rad);

        return new StepMetrics(distance, (int) calculateDirection(lonDiff, lat2Rad, lat1Rad), calculateDuration(distance, baseFactor));
    }

    public List<Step> computeStepsMetrics(List<Step> steps, Flight currentFlight) {
        for (int i=0; i<steps.size(); i++){
            Step s = steps.get(i);
            if(s.getOrder() < steps.size()-1){
                StepMetrics metrics = calculateMetricsBetweenTwoPoints(s.getAirfield().getLatitude(), s.getAirfield().getLongitude(), steps.get(i+1).getAirfield().getLatitude(), steps.get(i+1).getAirfield().getLongitude(), currentFlight.getAircraft().getBaseFactor());
                s.setDistance(metrics.distance());
                s.setCap(metrics.direction());
                s.setDuration(metrics.duration());
            }else{
                s.setDistance(0.0);
                s.setCap(0);
                s.setDuration(0);
            }
        }
        return steps;
    }

    public Flight computeTotalMetrics(Flight currentFlight) {
        List<Step> steps = currentFlight.getSteps();

        double totalDistance = steps.stream().mapToDouble(Step::getDistance).sum();
        int totalDuration = steps.stream().mapToInt(Step::getDuration).sum();

        totalDistance = Math.round(totalDistance);

        currentFlight.setDistance(totalDistance);
        currentFlight.setDuration(totalDuration);

        FuelMetrics fuelMetrics = computeFuelMetrics(totalDuration, currentFlight.getAircraft().getConsumption());
        FuelReport fuelReport = currentFlight.getFuelReport();
        fuelReport.setFuelNeeded(fuelMetrics.fuelNeeded());
        fuelReport.setFuelOnBoard(fuelMetrics.fuelOnBoard());
        fuelReport.setFuelSecurityTenPercent(fuelMetrics.fuelSecurityTenPercent());
        fuelReport.setFuelReserve(fuelMetrics.fuelReserve());

        return currentFlight;
    }

    public FuelMetrics computeFuelMetrics(Integer flightDuration, Integer aircraftConsumption) {
        double fuelNeeded = Math.round(((double) flightDuration / 60 * aircraftConsumption) * 100.0) / 100.0;
        double fuelSecurityTenPercent = Math.round((fuelNeeded * 0.1) * 100.0) / 100.0; // 10% of the fuel needed
        double fuelReserve = Math.round((aircraftConsumption * 0.5) * 100.0) / 100.0; // 30min of the aircraft consumption
        double fuelOnBoard = Math.round((fuelNeeded + fuelSecurityTenPercent + fuelReserve) * 100.0) / 100.0;

        return new FuelMetrics(fuelNeeded, fuelOnBoard, fuelSecurityTenPercent, fuelReserve);
    }

    public FuelReport initFuelReport(Flight flight, FuelMetrics fuelMetrics) {
        return FuelReport.builder()
            .fuelNeeded(fuelMetrics.fuelNeeded())
            .fuelOnBoard(fuelMetrics.fuelOnBoard())
            .fuelSecurityTenPercent(fuelMetrics.fuelSecurityTenPercent())
            .fuelReserve(fuelMetrics.fuelReserve())
            .flight(flight)
            .build();
    }

    public Flight initFlight(User user, Aircraft aircraft, StepMetrics metrics) {

        return Flight.builder()
                .createdAt(LocalDateTime.now())
                .user(user)
                .isCurrentEdit(true)
                .distance(metrics.distance())
                .duration(metrics.duration())
                .aircraft(aircraft)
                .build();
    }

    public List<Step> initDepartureArrivalSteps(Airfield favoriteAirfield, StepMetrics metrics, Flight flight) {

        List<Step> steps = new ArrayList<>();
        Step stepDeparture = new Step();
        Step stepArrival = new Step();

        stepDeparture.setAirfield(favoriteAirfield);
        stepDeparture.setOrder(0);
        stepDeparture.setDistance(metrics.distance());
        stepDeparture.setCap(metrics.direction());
        stepDeparture.setDuration(metrics.duration());
        stepDeparture.setFlight(flight);

        stepArrival.setAirfield(favoriteAirfield);
        stepArrival.setOrder(1);
        stepArrival.setDistance(0.0);
        stepArrival.setCap(0);
        stepArrival.setDuration(0);
        stepArrival.setFlight(flight);

        steps.add(stepDeparture);
        steps.add(stepArrival);

        return steps;
    }

    private double calculateDistance(double latDiff, double lonDiff, double lat1Rad, double lat2Rad) {

        // Haversine formula to calculate the distance
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = 6371 * c; // Radius of the Earth in kilometers

        // Convert distance to nautical miles
        return Math.ceil((distance / 1.852));
    }

    private double calculateDirection(double lonDiff, double lat2Rad, double lat1Rad) {
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
        return directionDeg;
    }

    private int calculateDuration(double distance, Double baseFactor) {
        return (int) (distance * baseFactor);
    }

}
