package fr.gofly.xmlParser.export;

import fr.gofly.model.Part;
import fr.gofly.model.SiaExport;
import fr.gofly.repository.PartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartExportService {
    @Autowired
    private PartRepository partRepository;

    private final Logger logger = LoggerFactory.getLogger(PartExportService.class);

    /**
     * Exports parts to the database based on the parsed XML data.
     * This method is called internally by parseXMLFile().
     */
    public void exportPartsToDatabase(SiaExport siaExport) {
        try{
            logger.info("Parts export : STARTED");
            if (siaExport != null && siaExport.getParts() != null) {
                logger.info("Parts export : " + siaExport.getParts().size() + " parts found");

                for (Part part: siaExport.getParts()) {
                    savePartsToDatabase(part);
                }

                logger.info("Parts export : " + partRepository.countBy() + " parts inserted");
                if(siaExport.getParts().size() != partRepository.countBy()){
                    logger.warn("Parts export : Not all parts have been exported to the database");
                }
            }else{
                logger.warn("Parts export : No parts found in the XML file");
            }
        }catch (Exception e){
            logger.error("Error during export of parts to database: " + e.getMessage());
            throw new RuntimeException("Error during export of parts to database: " + e.getMessage(), e);
        }finally {
            logger.info("Parts export : FINISHED");
        }
    }

    /**
     * Saves a part to the database.
     * This method is called internally by exportPartsToDatabase().
     *
     * @param part The parts object to be saved.
     * @throws RuntimeException If an error occurs during the database operation.
     */
    private void savePartsToDatabase(Part part) {
        try {
            partRepository.save(part);
        } catch (Exception e) {
            logger.error("Error saving part to database: " + e.getMessage());
            //throw new RuntimeException("Error saving obstacle to database: " + e.getMessage(), e);
        }
    }
}
