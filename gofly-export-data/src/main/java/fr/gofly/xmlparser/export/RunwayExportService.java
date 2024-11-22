package fr.gofly.xmlparser.export;

import fr.gofly.model.SiaExport;
import fr.gofly.model.runway.Runway;
import fr.gofly.model.runway.RunwayType;
import fr.gofly.repository.AirfieldRepository;
import fr.gofly.repository.RunwayRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class RunwayExportService {

    private final RunwayRepository runwayRepository;
    private final AirfieldRepository airfieldRepository;

    public RunwayExportService(RunwayRepository runwayRepository, AirfieldRepository airfieldRepository){
        this.runwayRepository = runwayRepository;
        this.airfieldRepository = airfieldRepository;
    }

    public void exportRunwaysToDatabase(SiaExport siaExport){
        try {
            log.info("Runways export : STARTED");
            if(siaExport != null && siaExport.getRunways() != null){
                log.info("Runways export : {} runways found", siaExport.getRunways().size());

                for(Runway runway: siaExport.getRunways()){
                    runway.setType(determineRunwayType(runway.getRevetment()));
                    saveRunwayToDatabase(runway);
                }
                long count = airfieldRepository.count();
                log.info("Runways export: {} runways inserted", count);
                if(siaExport.getRunways().size() != count){
                    log.warn("Runways export: Not all runways have been exported to the database");
                }

            }else{
                log.warn("Runways export: No runways found in the XML file");
            }
        }catch (Exception e){
            throw new RuntimeException("Error during export of runway to database " + e.getMessage(), e);
        }finally {
            log.info("Runways export: FINSIHED");
        }
    }

    private RunwayType determineRunwayType(String revetment){
        Set<String> pavedRevetment = Set.of("asphalte", "béton", "béton bitumineux", "enrobé bitumineux", "hydrocarboné", "macadam", "revêtue", "tarmac");
        Set<String> unpavedRevetment = Set.of("gazon", "non revêtue");

        if(revetment == null){
            return RunwayType.UNPAVED;
        }

        if(pavedRevetment.contains(revetment)){
            return RunwayType.PAVED;
        }else if (unpavedRevetment.contains(revetment)){
            return RunwayType.UNPAVED;
        }else{
            return RunwayType.UNPAVED;
        }

    }

    private void saveRunwayToDatabase(Runway runway){
        try {
            runwayRepository.save(runway);
        } catch (Exception e){
            throw new RuntimeException("Error saving runway to database " + e.getMessage(), e);
        }
    }

}
