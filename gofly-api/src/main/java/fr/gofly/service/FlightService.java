package fr.gofly.service;

import fr.gofly.dto.FlightDto;
import fr.gofly.helper.FlightHelper;
import fr.gofly.mapper.FlightToFlightDto;
import fr.gofly.model.Flight;
import fr.gofly.model.FlightMetrics;
import fr.gofly.model.User;
import fr.gofly.model.airfield.Airfield;
import fr.gofly.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlightService {

    private static final Logger log = LoggerFactory.getLogger(FlightService.class);
    private final FlightHelper flightHelper;
    private final FlightRepository flightRepository;
    private final FlightToFlightDto flightMapper;

    public Optional<FlightDto> createFlight(Flight flight, User user) {
        if(flightHelper.isMissingMandatoryField(flight)) {
            return Optional.empty();
        }
        Airfield favoriteAirfield = user.getFavoriteAirfield();

        flight.setUser(user);
        flight.setCurrentEdit(true);
        flight.setAirfieldDeparture(favoriteAirfield);
        flight.setAirfieldArrival(favoriteAirfield);

        FlightMetrics metrics = flightHelper.calculateMetricsBetweenTwoPoints(flight.getAirfieldDeparture().getLatitude(), flight.getAirfieldDeparture().getLongitude(), flight.getAirfieldArrival().getLatitude(), flight.getAirfieldArrival().getLongitude());
        flight.setDistance(metrics.distance());
        flight.setDirection(metrics.direction());
        flight.setDuration(flightHelper.calculateDuration(metrics.distance(), flight.getAircraft().getBaseFactor()));

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

    public void archiveCurrentUserFlight(User user) {
        flightRepository.findFirstByUserAndIsCurrentEdit(user, true).ifPresent(f -> {
            f.setCurrentEdit(false);
            flightRepository.save(f);
        });
    }
}
