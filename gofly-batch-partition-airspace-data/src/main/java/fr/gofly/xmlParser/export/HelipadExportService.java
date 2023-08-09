package fr.gofly.xmlParser.export;

import fr.gofly.model.Helipad;
import fr.gofly.model.SiaExport;
import fr.gofly.repository.HelipadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelipadExportService {
    @Autowired
    private HelipadRepository helipadRepository;

    private final Logger logger = LoggerFactory.getLogger(HelipadExportService.class);

    /**
     * Exports helipad to the database based on the parsed XML data.
     * This method is called internally by parseXMLFile().
     */
    public void exportHelipadsToDatabase(SiaExport siaExport) {
        try{
            logger.info("Helipads export : STARTED");
            if (siaExport != null && siaExport.getHelipads() != null) {
                logger.info("Helipads export : " + siaExport.getHelipads().size() + " helipads found");

                for (Helipad helipad: siaExport.getHelipads()) {
                    saveHelipadToDatabase(helipad);
                }

                logger.info("Helipads export : " + helipadRepository.countBy() + " helipads inserted");
                if(siaExport.getHelipads().size() != helipadRepository.countBy()){
                    logger.warn("Helipads export : Not all helipads have been exported to the database");
                }
            }else{
                logger.warn("Helipads export : No helipads found in the XML file");
            }
        }catch (Exception e){
            logger.error("Error during export of helipads to database: " + e.getMessage());
            throw new RuntimeException("Error during export of helipads to database: " + e.getMessage(), e);
        }finally {
            logger.info("Helipads export : FINISHED");
        }
    }

    /**
     * Saves an helipad to the database.
     * This method is called internally by exportHelipadsToDatabase().
     *
     * @param helipad The helipad object to be saved.
     * @throws RuntimeException If an error occurs during the database operation.
     */
    private void saveHelipadToDatabase(Helipad helipad) {
        try {
            helipadRepository.save(helipad);
        } catch (Exception e) {
            logger.error("Error saving helipad to database: " + e.getMessage());
            throw new RuntimeException("Error saving helipad to database: " + e.getMessage(), e);
        }
    }
}
