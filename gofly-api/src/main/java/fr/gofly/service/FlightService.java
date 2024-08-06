package fr.gofly.service;

import fr.gofly.dto.FlightDto;
import fr.gofly.helper.FlightHelper;
import fr.gofly.mapper.FlightToFlightDto;
import fr.gofly.model.Aircraft;
import fr.gofly.model.Flight;
import fr.gofly.model.FlightMetrics;
import fr.gofly.model.User;
import fr.gofly.model.airfield.Airfield;
import fr.gofly.repository.AircraftRepository;
import fr.gofly.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightService {

    private static final Logger log = LoggerFactory.getLogger(FlightService.class);
    private final FlightHelper flightHelper;
    private final FlightRepository flightRepository;
    private final FlightToFlightDto flightMapper;
    private final AircraftRepository aircraftRepository;

    public Optional<FlightDto> createFlight(User user) {

        archiveCurrentUserFlight(user);

        Airfield favoriteAirfield = user.getFavoriteAirfield();
        Aircraft aircraft = aircraftRepository.findByFavoriteTrueAndUser(user);
        if(aircraft == null) {
            return Optional.empty();
        }

        FlightMetrics metrics = flightHelper.calculateMetricsBetweenTwoPoints(favoriteAirfield.getLatitude(), favoriteAirfield.getLongitude(), favoriteAirfield.getLatitude(), favoriteAirfield.getLongitude());
        int duration = flightHelper.calculateDuration(metrics.distance(), aircraft.getBaseFactor());

        Flight flight = Flight.builder()
                .createdAt(LocalDateTime.now())
                .user(user)
                .isCurrentEdit(true)
                .airfieldDeparture(favoriteAirfield)
                .airfieldArrival(favoriteAirfield)
                .distance(metrics.distance())
                .direction(metrics.direction())
                .duration(duration)
                .aircraft(aircraft)
                .build();
        return Optional.of(flightMapper.map(flightRepository.save(flight)));
    }

    public Optional<FlightDto> getCurrentFlight(User user) {
        return flightRepository.findFirstByUserAndIsCurrentEdit(user, true).map(flightMapper::map);
    }

    public Optional<FlightDto> updateFlight(Flight flight, User user) {
        flight.setUser(user);
        FlightMetrics metrics = flightHelper.calculateMetricsBetweenTwoPoints(flight.getAirfieldDeparture().getLatitude(), flight.getAirfieldDeparture().getLongitude(), flight.getAirfieldArrival().getLatitude(), flight.getAirfieldArrival().getLongitude());
        flight.setDistance(metrics.distance());
        flight.setDirection(metrics.direction());
        flight.setDuration(flightHelper.calculateDuration(metrics.distance(), flight.getAircraft().getBaseFactor()));

        return Optional.of(flightMapper.map(flightRepository.save(flight)));
    }

    private void archiveCurrentUserFlight(User user) {
        flightRepository.findFirstByUserAndIsCurrentEdit(user, true).ifPresent(f -> {
            f.setCurrentEdit(false);
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
            f.setCurrentEdit(false);
            flightRepository.save(f);
        });
        flight.setCurrentEdit(true);
        flight.setUser(user);
        return Optional.of(flightMapper.map(flightRepository.save(flight)));
    }

    public Optional<FlightDto> setAircraft(Aircraft aircraft, User user) {
        Optional<Flight> currentFlight = flightRepository.findFirstByUserAndIsCurrentEdit(user, true);
        if(currentFlight.isEmpty()) {
            return Optional.empty();
        }
        currentFlight.get().setAircraft(aircraft);
        FlightMetrics metrics = flightHelper.calculateMetricsBetweenTwoPoints(currentFlight.get().getAirfieldDeparture().getLatitude(), currentFlight.get().getAirfieldDeparture().getLongitude(), currentFlight.get().getAirfieldArrival().getLatitude(), currentFlight.get().getAirfieldArrival().getLongitude());
        currentFlight.get().setDistance(metrics.distance());
        currentFlight.get().setDuration(flightHelper.calculateDuration(metrics.distance(), aircraft.getBaseFactor()));
        return Optional.of(flightMapper.map(flightRepository.save(currentFlight.get())));
    }

    public Optional<FlightDto> setAirfield(Airfield airfield, User user, String typeAirfield) {
        Optional<Flight> currentFlight = flightRepository.findFirstByUserAndIsCurrentEdit(user, true);
        if(currentFlight.isEmpty()) {
            return Optional.empty();
        }
        if("departure".equals(typeAirfield)) {
            currentFlight.get().setAirfieldDeparture(airfield);
        } else {
            currentFlight.get().setAirfieldArrival(airfield);
        }
        FlightMetrics metrics = flightHelper.calculateMetricsBetweenTwoPoints(currentFlight.get().getAirfieldDeparture().getLatitude(), currentFlight.get().getAirfieldDeparture().getLongitude(), currentFlight.get().getAirfieldArrival().getLatitude(), currentFlight.get().getAirfieldArrival().getLongitude());
        currentFlight.get().setDistance(metrics.distance());
        currentFlight.get().setDirection(metrics.direction());
        currentFlight.get().setDuration(flightHelper.calculateDuration(metrics.distance(), currentFlight.get().getAircraft().getBaseFactor()));
        return Optional.of(flightMapper.map(flightRepository.save(currentFlight.get())));
    }
}
