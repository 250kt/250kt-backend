package fr.gofly.xmlparser.export;

import fr.gofly.model.Border;
import fr.gofly.model.SiaExport;
import fr.gofly.repository.BorderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BorderExportService {
    private final BorderRepository borderRepository;

    private final Logger logger = LoggerFactory.getLogger(BorderExportService.class);

    public BorderExportService(BorderRepository borderRepository) {
        this.borderRepository = borderRepository;
    }

    /**
     * Exports borders to the database based on the parsed XML data.
     * This method is called internally by parseXMLFile().
     */
    public void exportBordersToDatabase(SiaExport siaExport) {
        try{
            logger.info("Borders export : STARTED");
            if (siaExport != null && siaExport.getBorders() != null) {
                logger.info("Borders export : {} borders found", siaExport.getBorders().size());

                for (Border border: siaExport.getBorders()) {
                    saveBorderToDatabase(border);
                }

                logger.info("Borders export : {} borders inserted", borderRepository.countBy());
                if(siaExport.getBorders().size() != borderRepository.countBy()){
                    logger.warn("Borders export : Not all borders have been exported to the database");
                }
            }else{
                logger.warn("Borders export : No borders found in the XML file");
            }
        }catch (Exception e){
            throw new RuntimeException("Error during export of border to database: " + e.getMessage(), e);
        }finally {
            logger.info("Borders export : FINISHED");
        }
    }

    /**
     * Saves a border to the database.
     * This method is called internally by exportBordersToDatabase().
     *
     * @param border The border object to be saved.
     * @throws RuntimeException If an error occurs during the database operation.
     */
    private void saveBorderToDatabase(Border border) {
        try {
            borderRepository.save(border);
        } catch (Exception e) {
            throw new RuntimeException("Error saving border to database: " + e.getMessage(), e);
        }
    }
}