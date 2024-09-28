package fr.gofly.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class FlightDto {

    private Long id;
    private String createdAt;
    private AircraftDto aircraft;
    private Boolean isCurrentEdit;
    private Double distance;
    private Integer duration;
    private FuelReportDto fuelReport;
    private List<StepDto> steps;
}
