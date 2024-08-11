package fr.gofly.mapper;

import fr.gofly.dto.StepDto;
import fr.gofly.model.flight.Step;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StepToStepDto {

    private final AirfieldToAirfieldDto airfieldMapper;

    public StepDto map(Step step){
        return StepDto.builder()
            .id(step.getId())
            .altitude(step.getAltitude())
            .cap(step.getCap())
            .distance(step.getDistance())
            .duration(step.getDuration())
            .order(step.getOrder())
            .airfield(airfieldMapper.map(step.getAirfield()))
            .build();
    }
}
