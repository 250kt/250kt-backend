package fr.gofly.xmlParser;

import fr.gofly.model.Airfield;
import fr.gofly.model.SiaExport;
import fr.gofly.repository.AirfieldRepository;
import fr.gofly.repository.ObstacleRepository;
import fr.gofly.xmlParser.export.AirfieldExportService;
import fr.gofly.xmlParser.export.ObstacleExportService;
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
    private Logger logger = LoggerFactory.getLogger(XmlParserService.class);
    private SiaExport siaExport;

    @Autowired
    private AirfieldExportService airfieldExportService;

    @Autowired
    private ObstacleExportService obstacleExportService;


    /**
     * Parses the XML file and exports airfields to the database.
     * This method is automatically executed after the Spring bean is constructed.
     */
    @PostConstruct
    public void parseXMLFile() {
        try {
            logger.info("XML Parser : STARTED");

            File xmlFile = ResourceUtils.getFile("classpath:" + xmlFilePath);

            JAXBContext jaxbContext = JAXBContext.newInstance(SiaExport.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            siaExport = (SiaExport) jaxbUnmarshaller.unmarshal(xmlFile);

            airfieldExportService.exportAirfieldsToDatabase(siaExport);
            obstacleExportService.exportObstaclesToDatabase(siaExport);

        } catch (Exception e) {
            logger.error("An error occurred during the process : " + e.getMessage());
            throw new RuntimeException("An error occurred during the process : " + e.getMessage(), e);
        } finally {
            logger.info("XML Parser : FINISHED");
        }
    }
}
