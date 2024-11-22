package fr.gofly.mapper;

import fr.gofly.dto.AirfieldDto;
import fr.gofly.model.airfield.Airfield;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AirfieldToAirfieldDto {

    private final RunwayToRunwayDto runwayMapper;

    public AirfieldDto map(Airfield airfield) {
        log.info(airfield.getCode());
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
                .runway(runwayMapper.map(airfield.getMainRunway()))
                .build();
    }
}
