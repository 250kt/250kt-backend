package fr.gofly.mapper;

import fr.gofly.dto.TafDto;
import fr.gofly.model.Taf;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TafDtoMapper {

    public TafDto map(Taf taf){
        return TafDto.builder()
                .publishedAt(taf.getPublishedAt())
                .data(taf.getData())
                .build();
    }

}
