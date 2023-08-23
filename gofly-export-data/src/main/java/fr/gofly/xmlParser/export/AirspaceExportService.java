package fr.gofly.xmlParser.export;

import fr.gofly.model.Airspace;
import fr.gofly.model.SiaExport;
import fr.gofly.repository.AirspaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirspaceExportService {
    @Autowired
    private AirspaceRepository airspaceRepository;

    private final Logger logger = LoggerFactory.getLogger(AirspaceExportService.class);

    /**
     * Exports airspaces to the database based on the parsed XML data.
     * This method is called internally by parseXMLFile().
     */
    public void exportAirspacesToDatabase(SiaExport siaExport) {
        try{
            logger.info("Airspaces export : STARTED");
            if (siaExport != null && siaExport.getAirspaces() != null) {
                logger.info("Airspaces export : " + siaExport.getAirspaces().size() + " airspaces found");

                for (Airspace airspace: siaExport.getAirspaces()) {
                    saveAirspaceToDatabase(airspace);
                }

                logger.info("Airspaces export : " + airspaceRepository.countBy() + " airspaces inserted");
                if(siaExport.getAirspaces().size() != airspaceRepository.countBy()){
                    logger.warn("Airspaces export : Not all airspaces have been exported to the database");
                }
            }else{
                logger.warn("Airspaces export : No airspaces found in the XML file");
            }
        }catch (Exception e){
            logger.error("Error during export of airspace to database: " + e.getMessage());
            throw new RuntimeException("Error during export of airspace to database: " + e.getMessage(), e);
        }finally {
            logger.info("Airspaces export : FINISHED");
        }
    }

    /**
     * Saves an airspace to the database.
     * This method is called internally by exportAirspacesToDatabase().
     *
     * @param airspace The airspace object to be saved.
     * @throws RuntimeException If an error occurs during the database operation.
     */
    private void saveAirspaceToDatabase(Airspace airspace) {
        try {
            airspaceRepository.save(airspace);
        } catch (Exception e) {
            logger.error("Error saving airspace to database: " + e.getMessage());
            //throw new RuntimeException("Error saving airspace to database: " + e.getMessage(), e);
        }
    }
}

