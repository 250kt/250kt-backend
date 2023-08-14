package fr.gofly.xmlParser.export;

import fr.gofly.model.Airfield;
import fr.gofly.model.Obstacle;
import fr.gofly.model.SiaExport;
import fr.gofly.repository.AirfieldRepository;
import fr.gofly.repository.ObstacleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObstacleExportService {
    @Autowired
    private ObstacleRepository obstacleRepository;

    private final Logger logger = LoggerFactory.getLogger(ObstacleExportService.class);

    /**
     * Exports obstacles to the database based on the parsed XML data.
     * This method is called internally by parseXMLFile().
     */
    public void exportObstaclesToDatabase(SiaExport siaExport) {
        try{
            logger.info("Obstacles export : STARTED");
            if (siaExport != null && siaExport.getObstacles() != null) {
                logger.info("Obstacles export : " + siaExport.getObstacles().size() + " obstacles found");

                for (Obstacle obstacle: siaExport.getObstacles()) {
                    saveObstacleToDatabase(obstacle);
                }

                logger.info("Obstacles export : " + obstacleRepository.countBy() + " obstacles inserted");
                if(siaExport.getObstacles().size() != obstacleRepository.countBy()){
                    logger.warn("Obstacles export : Not all obstacles have been exported to the database");
                }
            }else{
                logger.warn("Obstacles export : No obstacles found in the XML file");
            }
        }catch (Exception e){
            logger.error("Error during export of obstacles to database: " + e.getMessage());
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
            logger.error("Error saving obstacle to database: " + e.getMessage());
            //throw new RuntimeException("Error saving obstacle to database: " + e.getMessage(), e);
        }
    }
}
