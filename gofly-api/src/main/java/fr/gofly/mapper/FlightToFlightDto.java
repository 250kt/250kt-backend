package fr.gofly.mapper;

import fr.gofly.dto.FlightDto;
import fr.gofly.model.Flight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlightToFlightDto {

    private final AircraftToAircraftDto aircraftMapper;
    private final UserToUserDto userMapper;
    private final AirfieldToAirfieldDto airfieldMapper;

    public FlightDto map(Flight flight){
        return FlightDto.builder()
            .id(flight.getId())
            .aircraft(aircraftMapper.map(flight.getAircraft()))
            .createdAt(String.valueOf(flight.getCreatedAt()))
            .airfieldDeparture(airfieldMapper.map(flight.getAirfieldDeparture()))
            .airfieldArrival(airfieldMapper.map(flight.getAirfieldArrival()))
            .isCurrentEdit(flight.isCurrentEdit())
            .build();
    }

}
