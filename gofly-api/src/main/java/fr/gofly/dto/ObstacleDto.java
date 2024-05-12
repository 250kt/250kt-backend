package fr.gofly.dto;

import fr.gofly.model.Territory;
import fr.gofly.model.obstacle.ObstacleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ObstacleDto {

    private Integer id;
    private Territory territory;
    private Double latitude;
    private Double longitude;
    private Double altitude;
    private Double height;
    private ObstacleType type;
    private Integer count;

}
