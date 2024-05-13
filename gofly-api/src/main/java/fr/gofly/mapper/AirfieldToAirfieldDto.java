package fr.gofly.mapper;

import fr.gofly.dto.AirfieldDto;
import fr.gofly.model.airfield.Airfield;
import org.springframework.stereotype.Component;

@Component
public class AirfieldToAirfieldDto {

    public AirfieldDto map(Airfield airfield) {
        return AirfieldDto.builder()
                .id(airfield.getId())
                .fullName(airfield.getFullName())
                .code(airfield.getCode())
                .latitude(airfield.getLatitude())
                .longitude(airfield.getLongitude())
                .altitude(airfield.getAltitude())
                .territory(airfield.getTerritory())
                .status(airfield.getStatus())
                .type(airfield.getType())
                .build();
    }
}
