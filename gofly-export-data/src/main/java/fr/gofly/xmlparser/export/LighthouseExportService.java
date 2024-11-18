package fr.gofly.xmlparser.export;

import fr.gofly.model.Lighthouse;
import fr.gofly.model.SiaExport;
import fr.gofly.repository.LighthouseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LighthouseExportService {
    private final LighthouseRepository lighthouseRepository;

    private final Logger logger = LoggerFactory.getLogger(LighthouseExportService.class);

    public LighthouseExportService(LighthouseRepository lighthouseRepository) {
        this.lighthouseRepository = lighthouseRepository;
    }

    /**
     * Exports lighthouse to the database based on the parsed XML data.
     * This method is called internally by parseXMLFile().
     */
    public void exportLighthousesToDatabase(SiaExport siaExport) {
        try{
            logger.info("Lighthouses export : STARTED");
            if (siaExport != null && siaExport.getLighthouses() != null) {
                logger.info("Lighthouses export : {} lighthouses found", siaExport.getLighthouses().size());

                for (Lighthouse lighthouse: siaExport.getLighthouses()) {
                    saveLighthouseToDatabase(lighthouse);
                }

                logger.info("Lighthouses export : {} lighthouses inserted", lighthouseRepository.countBy());
                if(siaExport.getLighthouses().size() != lighthouseRepository.countBy()){
                    logger.warn("Lighthouses export : Not all lighthouses have been exported to the database");
                }
            }else{
                logger.warn("Lighthouses export : No lighthouses found in the XML file");
            }
        }catch (Exception e){
            throw new RuntimeException("Error during export of lighthouses to database: " + e.getMessage(), e);
        }finally {
            logger.info("Lighthouses export : FINISHED");
        }
    }

    /**
     * Saves an lighthouse to the database.
     * This method is called internally by exportLighthousesToDatabase().
     *
     * @param lighthouse The lighthouse object to be saved.
     * @throws RuntimeException If an error occurs during the database operation.
     */
    private void saveLighthouseToDatabase(Lighthouse lighthouse) {
        try {
            lighthouseRepository.save(lighthouse);
        } catch (Exception e) {
            throw new RuntimeException("Error saving lighthouse to database: " + e.getMessage(), e);
        }
    }
}
