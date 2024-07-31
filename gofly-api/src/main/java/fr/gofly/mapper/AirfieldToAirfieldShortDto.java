package fr.gofly.mapper;

import fr.gofly.dto.AirfieldShortDto;
import fr.gofly.model.airfield.Airfield;
import org.springframework.stereotype.Component;

@Component
public class AirfieldToAirfieldShortDto {

    public AirfieldShortDto map(Airfield airfield) {
        return AirfieldShortDto.builder()
                .id(airfield.getId())
                .fullName(airfield.getFullName())
                .code(airfield.getCode())
                .latitude(airfield.getLatitude())
                .longitude(airfield.getLongitude())
                .territory(airfield.getTerritory())
                .build();
    }
}
