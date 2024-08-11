package fr.gofly.mapper;

import fr.gofly.dto.FlightDto;
import fr.gofly.model.flight.Flight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FlightToFlightDto {

    private final AircraftToAircraftDto aircraftMapper;
    private final StepToStepDto stepToStepDto;

    public FlightDto map(Flight flight){
        return FlightDto.builder()
            .id(flight.getId())
            .aircraft(aircraftMapper.map(flight.getAircraft()))
            .createdAt(String.valueOf(flight.getCreatedAt()))
            .isCurrentEdit(flight.getIsCurrentEdit())
            .distance(flight.getDistance())
            .duration(flight.getDuration())
            .steps(flight.getSteps().stream().map(stepToStepDto::map).collect(Collectors.toList()))
            .build();
    }

}
