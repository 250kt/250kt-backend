package fr.gofly.xmlparser.export;

import fr.gofly.model.obstacle.Obstacle;
import fr.gofly.model.SiaExport;
import fr.gofly.model.obstacle.ObstacleBeaconing;
import fr.gofly.model.obstacle.ObstacleType;
import fr.gofly.repository.ObstacleRepository;
import fr.gofly.service.ObstacleMergeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ObstacleExportService {

    private final ObstacleRepository obstacleRepository;
    private final ObstacleMergeService obstacleMergeService;

    private final Logger logger = LoggerFactory.getLogger(ObstacleExportService.class);

    /**
     * Exports obstacles to the database based on the parsed XML data.
     * This method is called internally by parseXMLFile().
     */
    public void exportObstaclesToDatabase(SiaExport siaExport) {
        try{
            logger.info("Obstacles export : STARTED");
            if (siaExport != null && siaExport.getObstacles() != null) {
                logger.info("Obstacles export : {} obstacles found", siaExport.getObstacles().size());

                Map<Boolean, List<Obstacle>> partitionedObstacles = siaExport.getObstacles().stream()
                        .collect(Collectors.partitioningBy(obstacle -> obstacle.getTypeObst().contains("Eolienne")));

                List<Obstacle> windTurbineObstacles = partitionedObstacles.get(true);
                logger.info("Obstacles export : {} wind turbines found", windTurbineObstacles.size());

                List<Obstacle> windTurbines = obstacleMergeService.mergeObstacles(windTurbineObstacles);
                logger.info("Obstacles export : {} wind turbines after merging", windTurbines.size());

                List<Obstacle> obstacles = partitionedObstacles.get(false);

                obstacles.addAll(windTurbines);
                logger.info("Obstacles export : {} obstacles after merging", obstacles.size());

                for (Obstacle obstacle: obstacles) {
                    if(obstacle.getHeight() > 300){
                        obstacle.setType(determineObstacleType(obstacle));
                        saveObstacleToDatabase(obstacle);
                    }
                }

                logger.info("Obstacles export : {} obstacles inserted", obstacleRepository.countBy());
                if(siaExport.getObstacles().size() != obstacleRepository.countBy()){
                    logger.warn("Obstacles export : Not all obstacles have been exported to the database");
                }
            }else{
                logger.warn("Obstacles export : No obstacles found in the XML file");
            }
        }catch (Exception e){
            logger.error("Error during export of obstacles to database: {}", e.getMessage());
            throw new RuntimeException("Error during export of obstacles to database: " + e.getMessage(), e);
        }finally {
            logger.info("Obstacles export : FINISHED");
        }
    }

    /**
     * Saves an obstacle to the database.
     * This method is called internally by exportObstaclesToDatabase().
     *
     * @param obstacle The obstacle object to be saved.
     * @throws RuntimeException If an error occurs during the database operation.
     */
    private void saveObstacleToDatabase(Obstacle obstacle) {
        try {
            obstacleRepository.save(obstacle);
        } catch (Exception e) {
            throw new RuntimeException("Error saving obstacle to database: " + e.getMessage(), e);
        }
    }

    private ObstacleType determineObstacleType(Obstacle obstacle){
        if (obstacle.getTypeObst().contains("Eolienne")) {
            return ObstacleType.WIND_TURBINE;
        }

        boolean isHeightBelow500 = obstacle.getHeight() < 500;
        boolean isLightedAtNight = obstacle.getBeaconing() == ObstacleBeaconing.DAY_AND_NIGHT || obstacle.getBeaconing() == ObstacleBeaconing.NIGHT;
        boolean isGroup = obstacle.getCount() > 1;

        if(isHeightBelow500){
            if(isLightedAtNight){
                if(isGroup){
                    return ObstacleType.GROUP_OF_OBSTACLES_LIGHTED_AT_NIGHT_UNDER_500_AGL;
                }
                return ObstacleType.OBSTACLE_LIGHTED_AT_NIGHT_UNDER_500_AGL;
            }else{
                if(obstacle.getCount() > 1){
                    return ObstacleType.GROUP_OF_OBSTACLES_NOT_LIGHTED_AT_NIGHT_UNDER_500_AGL;
                }
                return ObstacleType.OBSTACLE_NOT_LIGHTED_AT_NIGHT_UNDER_500_AGL;
            }
        }else{
            if(isLightedAtNight){
                return isGroup ? ObstacleType.GROUP_OF_OBSTACLES_LIGHTED_AT_NIGHT_ABOVE_500_AGL : ObstacleType.OBSTACLE_LIGHTED_AT_NIGHT_ABOVE_500_AGL;
            }else{
                return isGroup ? ObstacleType.GROUP_OF_OBSTACLES_NOT_LIGHTED_AT_NIGHT_ABOVE_500_AGL : ObstacleType.OBSTACLE_NOT_LIGHTED_AT_NIGHT_ABOVE_500_AGL;
            }
        }
    }
}
