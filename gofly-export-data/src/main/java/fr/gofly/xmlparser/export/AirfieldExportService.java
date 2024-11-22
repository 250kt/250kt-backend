package fr.gofly.xmlparser.export;

import fr.gofly.model.airfield.Airfield;
import fr.gofly.model.SiaExport;
import fr.gofly.model.airfield.AirfieldType;
import fr.gofly.model.runway.Runway;
import fr.gofly.model.runway.RunwayType;
import fr.gofly.repository.AirfieldRepository;
import fr.gofly.repository.RunwayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AirfieldExportService {
    private final AirfieldRepository airfieldRepository;
    private final RunwayRepository runwayRepository;

    private final Logger logger = LoggerFactory.getLogger(AirfieldExportService.class);

    public AirfieldExportService(AirfieldRepository airfieldRepository, RunwayRepository runwayRepository) {
        this.airfieldRepository = airfieldRepository;
        this.runwayRepository = runwayRepository;
    }

    /**
     * Exports airfields to the database based on the parsed XML data.
     * This method is called internally by parseXMLFile().
     */
    public void exportAirfieldsToDatabase(SiaExport siaExport) {
        try{
            logger.info("Airfields export : STARTED");
            if (siaExport != null && siaExport.getAirfields() != null) {
                logger.info("Airfields export : {} airfields found", siaExport.getAirfields().size());

                for (Airfield airfield: siaExport.getAirfields()) {
                    saveAirfieldToDatabase(airfield);
                }

                logger.info("Airfields export : {} airfields inserted", airfieldRepository.countBy());
                if(siaExport.getAirfields().size() != airfieldRepository.countBy()){
                    logger.warn("Airfields export : Not all airfields have been exported to the database");
                }
            }else{
                logger.warn("Airfields export : No airfields found in the XML file");
            }
        }catch (Exception e){
            throw new RuntimeException("Error during export of airfield to database: " + e.getMessage(), e);
        }finally {
            logger.info("Airfields export : FINISHED");
        }
    }

    public void updateAirfieldsType(){
        List<Airfield> airfields = airfieldRepository.findAll();
        for(Airfield airfield: airfields){
            List<Runway> runways = runwayRepository.getRunwaysByAirfield(airfield);
            Runway mainRunway = null;
            if(!runways.isEmpty()){
                mainRunway = runways.stream().filter(Runway::isMain).findFirst().orElse(runways.get(0));
            }
            if(mainRunway != null){
                airfield.setType(determineAirfieldType(airfield, mainRunway));
            }else{
                airfield.setType(AirfieldType.ABANDONED);
            }
            airfield.setMainRunway(mainRunway);
        }
        airfieldRepository.saveAll(airfields);
    }

    /**
     * Determines the type of airfield based on its status.
     * @param airfield The {@link Airfield}
     * @return The {@link AirfieldType} of the airfield.
     */
    private AirfieldType determineAirfieldType(Airfield airfield, Runway runway) {
        Map<String, AirfieldType> staticTypes = Map.of(
                "OFF", AirfieldType.ABANDONED,
                "PRV", AirfieldType.PRIVATE,
                "TPD", AirfieldType.HOSPITAL,
                "ADM", AirfieldType.MILITARY_PAVED
        );

        AirfieldType type = staticTypes.get(airfield.getStatus());
        if (type != null) {
            return type;
        }

        return switch (airfield.getStatus()) {
            case "RST", "CAP" -> Objects.equals(runway.getType(), RunwayType.PAVED)
                    ? AirfieldType.CIVIL_PAVED
                    : AirfieldType.CIVIL_UNPAVED;
            case "MIL" -> Objects.equals(runway.getType(), RunwayType.PAVED)
                    ? AirfieldType.MILITARY_PAVED
                    : AirfieldType.MILITARY_UNPAVED;
            default -> AirfieldType.UNKNOWN;
        };
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
            if(respectConditions(airfield)){
                airfieldRepository.save(airfield);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error saving airfield to database: " + e.getMessage(), e);
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
