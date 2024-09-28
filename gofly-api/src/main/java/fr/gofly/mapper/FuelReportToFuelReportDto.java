package fr.gofly.mapper;

import fr.gofly.dto.FuelReportDto;
import fr.gofly.model.flight.FuelReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FuelReportToFuelReportDto {

    public FuelReportDto map(FuelReport fuelReport) {
        return FuelReportDto.builder()
            .fuelNeeded(fuelReport.getFuelNeeded())
            .fuelOnBoard(fuelReport.getFuelOnBoard())
            .fuelReserve(fuelReport.getFuelReserve())
            .fuelSecurityTenPercent(fuelReport.getFuelSecurityTenPercent())
            .build();
    }
}
