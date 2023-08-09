package fr.gofly.xmlParser;

import fr.gofly.model.Airfield;
import fr.gofly.model.SiaExport;
import fr.gofly.repository.AirfieldRepository;
import jakarta.annotation.PostConstruct;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import java.io.File;

@Service
public class XmlParserService {

    private final String xmlFilePath = "import.xml";

    @Autowired
    private AirfieldRepository airfieldRepository;

    private Logger logger = LoggerFactory.getLogger(XmlParserService.class);

    private SiaExport siaExport;

    @PostConstruct
    public void parseXMLFile(){
        try{
            logger.info("XML Parser : STARTED");

            File xmlFile = ResourceUtils.getFile("classpath:" + xmlFilePath);

            JAXBContext jaxbContext = JAXBContext.newInstance(SiaExport.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            siaExport = (SiaExport) jaxbUnmarshaller.unmarshal(xmlFile);

            exportAirfieldsToDatabase();

        } catch (Exception e){
            logger.error("An error occurred during the process : " + e.getMessage());
            throw new RuntimeException("An error occurred during the process : " + e.getMessage(), e);
        } finally {
            logger.info("XML Parser : FINISHED");
        }
    }

    private void exportAirfieldsToDatabase() {
        try{
            logger.info("Airfields export : STARTED");
            if (siaExport != null && siaExport.getAirfields() != null) {
                logger.info("Airfields export : " + siaExport.getAirfields().size() + " airfields found");

                for (Airfield airfield: siaExport.getAirfields()) {
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
            logger.error("Error during export of Airfield to database: " + e.getMessage());
            throw new RuntimeException("Error during export of Airfield to database: " + e.getMessage(), e);
        }finally {
            logger.info("Airfields export : FINISHED");
        }
    }

    private void saveAirfieldToDatabase(Airfield airfield) {
        try {
            airfieldRepository.save(airfield);
        } catch (Exception e) {
            logger.error("Error saving Airfield to database: " + e.getMessage());
            throw new RuntimeException("Error saving Airfield to database: " + e.getMessage(), e);
        }
    }
}
