package fr.gofly.mapper;

import fr.gofly.dto.AirspaceDto;
import fr.gofly.model.Airspace;
import org.springframework.stereotype.Component;

@Component
public class AirspaceToAirspaceDto {

    public AirspaceDto map(Airspace airspace){
        return AirspaceDto.builder()
            .id(airspace.getId())
            .name(airspace.getName())
            .type(airspace.getType())
            .build();
    }

}
