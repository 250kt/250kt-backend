package fr.gofly.xmlParser.export;

import fr.gofly.model.airfield.Airfield;
import fr.gofly.model.SiaExport;
import fr.gofly.model.airfield.AirfieldType;
import fr.gofly.repository.AirfieldRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AirfieldExportService {
    @Autowired
    private AirfieldRepository airfieldRepository;

    private final Logger logger = LoggerFactory.getLogger(AirfieldExportService.class);

    /**
     * Exports airfields to the database based on the parsed XML data.
     * This method is called internally by parseXMLFile().
     */
    public void exportAirfieldsToDatabase(SiaExport siaExport) {
        try{
            logger.info("Airfields export : STARTED");
            if (siaExport != null && siaExport.getAirfields() != null) {
                logger.info("Airfields export : " + siaExport.getAirfields().size() + " airfields found");

                for (Airfield airfield: siaExport.getAirfields()) {
                    airfield.setType(determineAirfieldType(airfield));
                    saveAirfieldToDatabase(airfield);
                }

                logger.info("Airfields export : " + airfieldRepository.countBy() + " airfields inserted");
                if(siaExport.getAirfields().size() != airfieldRepository.countBy()){
                    logger.warn("Airfields export : Not all airfields have been exported to the database");
                }
            }else{
                logger.warn("Airfields export : No airfields found in the XML file");
            }
        }catch (Exception e){
            logger.error("Error during export of airfield to database: " + e.getMessage());
            throw new RuntimeException("Error during export of airfield to database: " + e.getMessage(), e);
        }finally {
            logger.info("Airfields export : FINISHED");
        }
    }

    /**
     * Determines the type of airfield based on its status.
     * @param airfield The {@link Airfield}
     * @return The {@link AirfieldType} of the airfield.
     */
    private AirfieldType determineAirfieldType(Airfield airfield) {
        if(Objects.equals(airfield.getStatus(), "OFF")){
            return AirfieldType.ABANDONED;
        }
        else if(Objects.equals(airfield.getStatus(), "PRV")){
            return AirfieldType.PRIVATE;
        }
        else if(Objects.equals(airfield.getStatus(), "RST")){
            return AirfieldType.CIVIL_PAVED;//Gérer le cas des aérodromes civils non revêtus
        }
        else if(Objects.equals(airfield.getStatus(), "MIL")){
            return AirfieldType.MILITARY_PAVED;//Gérer le cas des aérodromes militaires non revêtus
        }
        else if(Objects.equals(airfield.getStatus(), "CAP")){
            return AirfieldType.CIVIL_PAVED;//Gérer le cas des aérodromes civils non revêtus
        }
        else{
            return AirfieldType.CIVIL_UNPAVED;
        }
        //Gérer le cas des aérodromes militaire et civil non revêtus et revêtus

    }

    /**
     * Saves an airfield to the database.
     * This method is called internally by exportAirfieldsToDatabase().
     *
     * @param airfield The airfield object to be saved.
     * @throws RuntimeException If an error occurs during the database operation.
     */
    private void saveAirfieldToDatabase(Airfield airfield) {
        try {
            if(respectConditions(airfield))
                airfieldRepository.save(airfield);
        } catch (Exception e) {
            logger.error("Error saving airfield to database: " + e.getMessage());
            //throw new RuntimeException("Error saving airfield to database: " + e.getMessage(), e);
        }
    }

    /**
     * Checks if the given airfield respects certain conditions.
     *
     * @param airfield The airfield to be checked.
     * @return {@code true} if the airfield meets the conditions, {@code false} otherwise.
     */
    private boolean respectConditions(Airfield airfield){
        // Exclude airfields with the "AUTRES" category
        return airfield.getTerritory().getId() != 9999;
    }

}
