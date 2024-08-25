package fr.gofly.mapper;

import fr.gofly.dto.MetarDto;
import fr.gofly.model.Metar;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MetarDtoMapper {

    public MetarDto map(Metar metar){
        return MetarDto.builder()
            .publishedAt(metar.getPublishedAt())
            .data(metar.getData())
            .build();
    }

}
