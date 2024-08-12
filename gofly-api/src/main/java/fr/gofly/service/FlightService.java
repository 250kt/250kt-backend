package fr.gofly.service;

import fr.gofly.dto.FlightDto;
import fr.gofly.helper.FlightHelper;
import fr.gofly.mapper.FlightToFlightDto;
import fr.gofly.model.Aircraft;
import fr.gofly.model.flight.Flight;
import fr.gofly.model.flight.FlightMetrics;
import fr.gofly.model.User;
import fr.gofly.model.airfield.Airfield;
import fr.gofly.model.flight.Step;
import fr.gofly.repository.AircraftRepository;
import fr.gofly.repository.FlightRepository;
import fr.gofly.repository.StepRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightService {

    private final FlightHelper flightHelper;
    private final FlightRepository flightRepository;
    private final FlightToFlightDto flightMapper;
    private final AircraftRepository aircraftRepository;
    private final StepRepository stepRepository;

    public Optional<FlightDto> createFlight(User user) {

        archiveCurrentUserFlight(user);

        Airfield favoriteAirfield = user.getFavoriteAirfield();
        Aircraft aircraft = aircraftRepository.findByFavoriteTrueAndUser(user);

        if(aircraft == null) {
            aircraft = aircraftRepository.findDefaultAircraft();
        }

        FlightMetrics metrics = flightHelper.calculateMetricsBetweenTwoPoints(favoriteAirfield.getLatitude(), favoriteAirfield.getLongitude(), favoriteAirfield.getLatitude(), favoriteAirfield.getLongitude());
        int duration = flightHelper.calculateDuration(metrics.distance(), aircraft.getBaseFactor());

        List<Step> steps = new ArrayList<>();
        Step stepDeparture = new Step();
        Step stepArrival = new Step();

        stepDeparture.setAirfield(favoriteAirfield);
        stepDeparture.setOrder(0);
        stepDeparture.setDistance(metrics.distance());
        stepDeparture.setCap(metrics.direction());
        stepDeparture.setDuration(duration);

        stepArrival.setAirfield(favoriteAirfield);
        stepArrival.setOrder(1);
        stepArrival.setDistance(0.0);
        stepArrival.setCap(0);
        stepArrival.setDuration(0);

        steps.add(stepDeparture);
        steps.add(stepArrival);

        Flight flight = Flight.builder()
                .createdAt(LocalDateTime.now())
                .user(user)
                .isCurrentEdit(true)
                .distance(metrics.distance())
                .duration(duration)
                .aircraft(aircraft)
                .build();

        flight = flightRepository.save(flight);
        flight.setSteps(steps);
        stepDeparture.setFlight(flight);
        stepArrival.setFlight(flight);
        stepRepository.saveAll(steps);

        return Optional.of(flightMapper.map(flightRepository.save(flight)));
    }

    public Optional<FlightDto> getCurrentFlight(User user) {
        Optional<Flight> currentFlightOptional = flightRepository.findFirstByUserAndIsCurrentEdit(user, true);
        if (currentFlightOptional.isEmpty()) {
            return Optional.empty();
        }
        Flight currentFlight = currentFlightOptional.get();
        currentFlight.getSteps().sort(Comparator.comparingInt(Step::getOrder));
        return Optional.of(flightMapper.map(currentFlight));
    }

    private void archiveCurrentUserFlight(User user) {
        flightRepository.findFirstByUserAndIsCurrentEdit(user, true).ifPresent(f -> {
            f.setIsCurrentEdit(false);
            flightRepository.save(f);
        });
    }

    public Optional<List<FlightDto>> getFlights(User user) {
        Optional<List<Flight>> flights = flightRepository.findAllByUserOrderByCurrentEditAndCreatedAtDesc(user);
        if(flights.isEmpty()) {
            return Optional.empty();
        }
        return flights.map(flight -> flight.stream().map(flightMapper::map).collect(Collectors.toList()));
    }

    public Optional<FlightDto> setCurrentFlight(Flight flight, User user) {
        Optional<Flight> currentFlight = flightRepository.findFirstByUserAndIsCurrentEdit(user, true);
        currentFlight.ifPresent(f -> {
            f.setIsCurrentEdit(false);
            flightRepository.save(f);
        });
        flight.setIsCurrentEdit(true);
        flight.setUser(user);
        flight.getSteps().forEach(s -> s.setFlight(flight));
        flight.getSteps().sort(Comparator.comparingInt(Step::getOrder));

        return Optional.of(flightMapper.map(flightRepository.save(flight)));
    }

    public Optional<FlightDto> setAircraft(Aircraft aircraft, User user) {
        Optional<Flight> currentFlightOptional = flightRepository.findFirstByUserAndIsCurrentEdit(user, true);
        if(currentFlightOptional.isEmpty()) {
            return Optional.empty();
        }
        Flight currentFlight = currentFlightOptional.get();
        currentFlight.setAircraft(aircraft);
        currentFlight = flightRepository.save(currentFlight);

        List<Step> steps = stepRepository.findAllByFlightOrderByOrder(currentFlight);

        stepRepository.saveAll(flightHelper.computeStepsMetrics(steps, currentFlight));
        currentFlight.getSteps().sort(Comparator.comparingInt(Step::getOrder));
        currentFlight = flightRepository.save(flightHelper.computeTotalMetrics(currentFlight));
        return Optional.of(flightMapper.map(currentFlight));
    }

    public Optional<FlightDto> setAirfieldStep(Airfield airfield, Long idStep, User user) {
        Optional<Flight> currentFlightOptional = flightRepository.findFirstByUserAndIsCurrentEdit(user, true);
        if(currentFlightOptional.isEmpty()) {
            return Optional.empty();
        }
        Flight currentFlight = currentFlightOptional.get();
        List<Step> steps = stepRepository.findAllByFlightOrderByOrder(currentFlight);
        steps.forEach(s -> {
            if(s.getId().equals(idStep)){
                s.setAirfield(airfield);
            }
        });

        stepRepository.saveAll(flightHelper.computeStepsMetrics(steps, currentFlight));
        currentFlight = flightRepository.save(flightHelper.computeTotalMetrics(currentFlight));
        currentFlight.getSteps().sort(Comparator.comparingInt(Step::getOrder));
        return Optional.of(flightMapper.map(currentFlight));

    }

    public Optional<FlightDto> addStep(User user) {
        Optional<Flight> currentFlightOptional = flightRepository.findFirstByUserAndIsCurrentEdit(user, true);
        if(currentFlightOptional.isEmpty()) {
            return Optional.empty();
        }
        Flight currentFlight = currentFlightOptional.get();
        Airfield favoriteAirfield = user.getFavoriteAirfield();

        List<Step> steps = stepRepository.findAllByFlightOrderByOrder(currentFlight);
        steps.get(steps.size()-1).setOrder(steps.size());
        Step step = new Step();
        step.setAirfield(favoriteAirfield);
        step.setOrder(currentFlight.getSteps().size()-1);
        step.setFlight(currentFlight);

        steps.add(step);

        stepRepository.saveAll(steps);
        steps = stepRepository.findAllByFlightOrderByOrder(currentFlight);
        stepRepository.saveAll(flightHelper.computeStepsMetrics(steps, currentFlight));
        currentFlight.setSteps(stepRepository.findAllByFlightOrderByOrder(currentFlight));
        currentFlight = flightRepository.save(flightHelper.computeTotalMetrics(currentFlight));

        return Optional.of(flightMapper.map(currentFlight));

    }

    public Optional<FlightDto> deleteStep(Long idStep, User user) {
        Optional<Flight> currentFlightOptional = flightRepository.findFirstByUserAndIsCurrentEdit(user, true);
        if (currentFlightOptional.isEmpty()) {
            return Optional.empty();
        }
        Flight currentFlight = currentFlightOptional.get();
        List<Step> steps = stepRepository.findAllByFlightOrderByOrder(currentFlight);

        Step stepToRemove = steps.stream()
                .filter(step -> step.getId().equals(idStep))
                .findFirst()
                .orElse(null);

        if (stepToRemove == null) {
            return Optional.empty();
        }

        steps.remove(stepToRemove);
        stepRepository.delete(stepToRemove);

        // Adjust the order of the remaining steps
        for (int i = 0; i < steps.size(); i++) {
            steps.get(i).setOrder(i);
        }

        stepRepository.saveAll(steps);
        steps = stepRepository.findAllByFlightOrderByOrder(currentFlight);
        stepRepository.saveAll(flightHelper.computeStepsMetrics(steps, currentFlight));
        currentFlight.setSteps(stepRepository.findAllByFlightOrderByOrder(currentFlight));
        currentFlight = flightRepository.save(flightHelper.computeTotalMetrics(currentFlight));

        return Optional.of(flightMapper.map(currentFlight));
    }

    public Optional<FlightDto> updateStepOrder(Long idFlight, Integer previousOrder, Integer currentOrder, User user) {
        Optional<Flight> currentFlightOptional = flightRepository.findFirstByUserAndIsCurrentEdit(user, true);
        if (currentFlightOptional.isEmpty()) {
            return Optional.empty();
        }

        Flight currentFlight = currentFlightOptional.get();
        List<Step> steps = stepRepository.findAllByFlightOrderByOrder(currentFlight);

        Step stepToMove = steps.stream()
                .filter(step -> step.getOrder().equals(previousOrder))
                .findFirst()
                .orElse(null);

        if (stepToMove == null) {
            return Optional.empty();
        }

        steps.remove(stepToMove);
        steps.add(currentOrder, stepToMove);

        // Adjust the order of the remaining steps
        for (int i = 0; i < steps.size(); i++) {
            steps.get(i).setOrder(i);
        }

        stepRepository.saveAll(steps);
        steps = stepRepository.findAllByFlightOrderByOrder(currentFlight);
        stepRepository.saveAll(flightHelper.computeStepsMetrics(steps, currentFlight));
        currentFlight.setSteps(stepRepository.findAllByFlightOrderByOrder(currentFlight));
        currentFlight = flightRepository.save(flightHelper.computeTotalMetrics(currentFlight));

        return Optional.of(flightMapper.map(currentFlight));

    }
}
