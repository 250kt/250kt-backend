package fr.gofly.xmlparser.export;

import fr.gofly.model.Frequency;
import fr.gofly.model.SiaExport;
import fr.gofly.repository.FrequencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FrequencyExportService {
    private final FrequencyRepository frequencyRepository;

    private final Logger logger = LoggerFactory.getLogger(FrequencyExportService.class);

    public FrequencyExportService(FrequencyRepository frequencyRepository) {
        this.frequencyRepository = frequencyRepository;
    }

    /**
     * Exports frequencies to the database based on the parsed XML data.
     * This method is called internally by parseXMLFile().
     */
    public void exportFrequenciesToDatabase(SiaExport siaExport) {
        try{
            logger.info("Frequencies export : STARTED");
            if (siaExport != null && siaExport.getFrequencies() != null) {
                logger.info("Frequencies export : {} frequencies found", siaExport.getFrequencies().size());

                for (Frequency frequency: siaExport.getFrequencies()) {
                    saveFrequencyToDatabase(frequency);
                }

                logger.info("Frequencies export : {} frequencies inserted", frequencyRepository.countBy());
                if(siaExport.getFrequencies().size() != frequencyRepository.countBy()){
                    logger.warn("Frequencies export : Not all frequencies have been exported to the database");
                }
            }else{
                logger.warn("Frequencies export : No frequencies found in the XML file");
            }
        }catch (Exception e){
            throw new RuntimeException("Error during export of frequency to database: " + e.getMessage(), e);
        }finally {
            logger.info("Frequencies export : FINISHED");
        }
    }

    /**
     * Saves a frequency to the database.
     * This method is called internally by exportFrequenciesToDatabase().
     *
     * @param frequency The frequency object to be saved.
     * @throws RuntimeException If an error occurs during the database operation.
     */
    private void saveFrequencyToDatabase(Frequency frequency) {
        try {
            frequencyRepository.save(frequency);
        } catch (Exception e) {
            throw new RuntimeException("Error saving frequency to database: " + e.getMessage(), e);
        }
    }
}