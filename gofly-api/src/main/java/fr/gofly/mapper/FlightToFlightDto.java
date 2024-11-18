package fr.gofly.mapper;

import fr.gofly.dto.FlightDto;
import fr.gofly.model.flight.Flight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlightToFlightDto {

    private final AircraftToAircraftDto aircraftMapper;
    private final StepToStepDto stepMapper;
    private final FuelReportToFuelReportDto fuelReportMapper;

    public FlightDto map(Flight flight){
        return FlightDto.builder()
            .id(flight.getId())
            .aircraft(aircraftMapper.map(flight.getAircraft()))
            .createdAt(String.valueOf(flight.getCreatedAt()))
            .isCurrentEdit(flight.getIsCurrentEdit())
            .distance(flight.getDistance())
            .duration(flight.getDuration())
            .fuelReport(fuelReportMapper.map(flight.getFuelReport()))
            .steps(flight.getSteps().stream().map(stepMapper::map).toList())
            .build();
    }

}
