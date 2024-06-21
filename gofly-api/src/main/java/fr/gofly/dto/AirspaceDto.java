package fr.gofly.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirspaceDto {

    private Integer id;
    private String name;
    private String type;
    private List<PartDto> part;

}
