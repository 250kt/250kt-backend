package fr.gofly.xmlparser.export;

import fr.gofly.model.Part;
import fr.gofly.model.SiaExport;
import fr.gofly.repository.PartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PartExportService {
    private final PartRepository partRepository;

    private final Logger logger = LoggerFactory.getLogger(PartExportService.class);

    public PartExportService(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    /**
     * Exports parts to the database based on the parsed XML data.
     * This method is called internally by parseXMLFile().
     */
    public void exportPartsToDatabase(SiaExport siaExport) {
        try{
            logger.info("Parts export : STARTED");
            if (siaExport != null && siaExport.getParts() != null) {
                logger.info("Parts export : {} parts found", siaExport.getParts().size());

                for (Part part: siaExport.getParts()) {
                    savePartsToDatabase(part);
                }

                logger.info("Parts export : {} parts inserted", partRepository.countBy());
                if(siaExport.getParts().size() != partRepository.countBy()){
                    logger.warn("Parts export : Not all parts have been exported to the database");
                }
            }else{
                logger.warn("Parts export : No parts found in the XML file");
            }
        }catch (Exception e){
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
            throw new RuntimeException("Error saving obstacle to database: " + e.getMessage(), e);
        }
    }
}
