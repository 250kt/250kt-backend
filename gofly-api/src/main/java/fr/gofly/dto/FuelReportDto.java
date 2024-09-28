package fr.gofly.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class FuelReportDto {

    private Double fuelOnBoard;
    private Double fuelSecurityTenPercent;
    private Double fuelReserve;
    private Double fuelNeeded;
}
