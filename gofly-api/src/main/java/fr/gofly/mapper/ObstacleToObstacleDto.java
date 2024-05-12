package fr.gofly.mapper;

import fr.gofly.dto.ObstacleDto;
import fr.gofly.model.obstacle.Obstacle;
import org.springframework.stereotype.Component;

@Component
public class ObstacleToObstacleDto {

    public ObstacleDto map(Obstacle obstacle){
        return ObstacleDto.builder()
            .id(obstacle.getId())
            .territory(obstacle.getTerritory())
            .latitude((double) obstacle.getLatitude())
            .longitude((double) obstacle.getLongitude())
            .altitude((double) obstacle.getAltitude())
            .height((double) obstacle.getHeight())
            .type(obstacle.getType())
            .count(obstacle.getCount())
            .build();
    }

}
