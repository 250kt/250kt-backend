package fr.gofly.service;

import fr.gofly.dto.FlightDto;
import fr.gofly.helper.FlightHelper;
import fr.gofly.mapper.FlightToFlightDto;
import fr.gofly.model.Flight;
import fr.gofly.model.User;
import fr.gofly.model.airfield.Airfield;
import fr.gofly.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlightService {

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
        return Optional.of(flightMapper.map(flightRepository.save(flight)));
    }

    public Optional<FlightDto> getCurrentFlight(User user) {
        return flightRepository.findFirstByUserAndIsCurrentEdit(user, true).map(flightMapper::map);
    }

    public Optional<FlightDto> updateFlight(Flight flight, User user) {
        flight.setUser(user);
        return Optional.of(flightMapper.map(flightRepository.save(flight)));
    }
}
