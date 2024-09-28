package fr.gofly.service;

import fr.gofly.dto.FlightDto;
import fr.gofly.helper.FlightHelper;
import fr.gofly.mapper.FlightToFlightDto;
import fr.gofly.model.Aircraft;
import fr.gofly.model.flight.*;
import fr.gofly.model.User;
import fr.gofly.model.airfield.Airfield;
import fr.gofly.repository.AircraftRepository;
import fr.gofly.repository.FlightRepository;
import fr.gofly.repository.StepRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

        StepMetrics metrics = flightHelper.calculateMetricsBetweenTwoPoints(favoriteAirfield.getLatitude(), favoriteAirfield.getLongitude(), favoriteAirfield.getLatitude(), favoriteAirfield.getLongitude(), aircraft.getBaseFactor());
        FuelMetrics fuelMetrics = flightHelper.computeFuelMetrics(metrics.duration(), aircraft.getConsumption());

        Flight flight = flightHelper.initFlight(user, aircraft, metrics);
        flight.setSteps(flightHelper.initDepartureArrivalSteps(favoriteAirfield, metrics, flight));
        flight.setFuelReport(flightHelper.initFuelReport(flight, fuelMetrics));

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

        List<Step> steps = stepRepository.findAllByFlightOrderByOrder(currentFlight);

        currentFlight.setSteps(flightHelper.computeStepsMetrics(steps, currentFlight));
        currentFlight.getSteps().sort(Comparator.comparingInt(Step::getOrder));
        return Optional.of(flightMapper.map(flightRepository.save(flightHelper.computeTotalMetrics(currentFlight))));
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

        currentFlight.setSteps(flightHelper.computeStepsMetrics(steps, currentFlight));
        currentFlight.getSteps().sort(Comparator.comparingInt(Step::getOrder));
        return Optional.of(flightMapper.map(flightRepository.save(flightHelper.computeTotalMetrics(currentFlight))));
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
        steps.sort(Comparator.comparingInt(Step::getOrder));
        currentFlight.setSteps(flightHelper.computeStepsMetrics(steps, currentFlight));

        return Optional.of(flightMapper.map(flightRepository.save(flightHelper.computeTotalMetrics(currentFlight))));
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

        currentFlight.setSteps(flightHelper.computeStepsMetrics(steps, currentFlight));
        return Optional.of(flightMapper.map(flightRepository.save(flightHelper.computeTotalMetrics(currentFlight))));
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

        currentFlight.setSteps(flightHelper.computeStepsMetrics(steps, currentFlight));
        return Optional.of(flightMapper.map(flightRepository.save(flightHelper.computeTotalMetrics(currentFlight))));
    }

    public boolean deleteFlight(Integer idFlight, User user) {
        Optional<Flight> flightOptional = flightRepository.findByIdAndUser(idFlight, user);
        if (flightOptional.isEmpty()) {
            return false;
        }
        flightRepository.delete(flightOptional.get());
        flightRepository.findFirstByUserAndIsCurrentEdit(user, false).ifPresent(f -> {
            f.setIsCurrentEdit(true);
            flightRepository.save(f);
        });
        return true;
    }
}
