package fr.gofly.xmlparser.export;

import fr.gofly.model.Territory;
import fr.gofly.model.SiaExport;
import fr.gofly.repository.TerritoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TerritoryExportService {
    private final TerritoryRepository territoryRepository;

    private final Logger logger = LoggerFactory.getLogger(TerritoryExportService.class);

    public TerritoryExportService(TerritoryRepository territoryRepository) {
        this.territoryRepository = territoryRepository;
    }

    /**
     * Exports territories to the database based on the parsed XML data.
     * This method is called internally by parseXMLFile().
     */
    public void exportTerritoriesToDatabase(SiaExport siaExport) {
        try{
            logger.info("Territories export : STARTED");
            if (siaExport != null && siaExport.getTerritories() != null) {
                logger.info("Territories export : {} territories found", siaExport.getTerritories().size());

                for (Territory territory: siaExport.getTerritories()) {
                    territory.setIdentificationCode(territory.getIdentificationCode().toUpperCase());
                    saveTerritoryToDatabase(territory);
                }

                logger.info("Territories export : {} territories inserted", territoryRepository.countBy());
                if(siaExport.getTerritories().size() != territoryRepository.countBy()){
                    logger.warn("Territories export : Not all territories have been exported to the database");
                }
            }else{
                logger.warn("Territories export : No territories found in the XML file");
            }
        }catch (Exception e){
            throw new RuntimeException("Error during export of territories to database: " + e.getMessage(), e);
        }finally {
            logger.info("Territories export : FINISHED");
        }
    }

    /**
     * Saves a territory to the database.
     * This method is called internally by exportTerritoriesToDatabase().
     *
     * @param territory The territories object to be saved.
     * @throws RuntimeException If an error occurs during the database operation.
     */
    private void saveTerritoryToDatabase(Territory territory) {
        try {
            territoryRepository.save(territory);
        } catch (Exception e) {
            throw new RuntimeException("Error saving territory to database: " + e.getMessage(), e);
        }
    }
}