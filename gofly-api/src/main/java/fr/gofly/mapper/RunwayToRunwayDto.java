package fr.gofly.mapper;

import fr.gofly.dto.RunwayDto;
import fr.gofly.model.runway.Runway;
import org.springframework.stereotype.Component;

@Component
public class RunwayToRunwayDto {

    public RunwayDto map(Runway runway){
        return RunwayDto.builder()
                .orientation(runway.getOrientation())
                .build();
    }

}
