package fr.gofly.xmlparser.export;

import fr.gofly.model.Radio;
import fr.gofly.model.SiaExport;
import fr.gofly.repository.RadioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RadioExportService {
    private final RadioRepository radioRepository;

    private final Logger logger = LoggerFactory.getLogger(RadioExportService.class);

    public RadioExportService(RadioRepository radioRepository) {
        this.radioRepository = radioRepository;
    }

    /**
     * Exports radios to the database based on the parsed XML data.
     * This method is called internally by parseXMLFile().
     */
    public void exportRadiosToDatabase(SiaExport siaExport) {
        try{
            logger.info("Radios export : STARTED");
            if (siaExport != null && siaExport.getRadios() != null) {
                logger.info("Radios export : {} radios found", siaExport.getRadios().size());

                for (Radio radio: siaExport.getRadios()) {
                    saveRadiosToDatabase(radio);
                }

                logger.info("Radios export : {} radios inserted", radioRepository.countBy());
                if(siaExport.getRadios().size() != radioRepository.countBy()){
                    logger.warn("Radios export : Not all radios have been exported to the database");
                }
            }else{
                logger.warn("Radios export : No radios found in the XML file");
            }
        }catch (Exception e){
            throw new RuntimeException("Error during export of radios to database: " + e.getMessage(), e);
        }finally {
            logger.info("Radios export : FINISHED");
        }
    }

    /**
     * Saves a radio to the database.
     * This method is called internally by exportRadiosToDatabase().
     *
     * @param radio The radios object to be saved.
     * @throws RuntimeException If an error occurs during the database operation.
     */
    private void saveRadiosToDatabase(Radio radio) {
        try {
            radioRepository.save(radio);
        } catch (Exception e) {
            throw new RuntimeException("Error saving obstacle to database: " + e.getMessage(), e);
        }
    }
}
