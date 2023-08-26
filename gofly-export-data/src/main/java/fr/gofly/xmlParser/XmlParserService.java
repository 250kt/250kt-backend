package fr.gofly.xmlParser;

import fr.gofly.model.SiaExport;
import fr.gofly.xmlParser.export.*;
import jakarta.annotation.PostConstruct;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import java.io.File;
import java.net.URL;

@Service
public class XmlParserService {
    private Logger logger = LoggerFactory.getLogger(XmlParserService.class);
    private SiaExport siaExport;

    @Autowired
    private TerritoryExportService territoryExportService;

    @Autowired
    private AirfieldExportService airfieldExportService;

    @Autowired
    private ObstacleExportService obstacleExportService;

    @Autowired
    private HelipadExportService helipadExportService;

    @Autowired
    private RadioExportService radioExportService;

    @Autowired
    private AirspaceExportService airspaceExportService;

    @Autowired
    private FrequencyExportService frequencyExportService;

    @Autowired
    private ServiceExportService serviceExportService;

    @Autowired
    private BorderExportService borderExportService;

    @Autowired
    private PartExportService partExportService;

    @Autowired
    private LighthouseExportService lighthouseExportService;

    /**
     * Parses the XML file and exports objects to the database.
     * This method is automatically executed after the Spring bean is constructed.
     */
    @PostConstruct
    public void parseXMLFile() {
        try {
            logger.info("XML Parser : STARTED");

            ClassLoader classLoader = getClass().getClassLoader();
            String xmlFilePath = "import.xml";
            URL resourceUrl = classLoader.getResource(xmlFilePath);

            File xmlFile = new File(resourceUrl.toURI());

            JAXBContext jaxbContext = JAXBContext.newInstance(SiaExport.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            siaExport = (SiaExport) jaxbUnmarshaller.unmarshal(xmlFile);

            territoryExportService.exportTerritoriesToDatabase(siaExport);
            borderExportService.exportBordersToDatabase(siaExport);
            airspaceExportService.exportAirspacesToDatabase(siaExport);
            partExportService.exportPartsToDatabase(siaExport);
            airfieldExportService.exportAirfieldsToDatabase(siaExport);
            obstacleExportService.exportObstaclesToDatabase(siaExport);
            lighthouseExportService.exportLighthousesToDatabase(siaExport);
            helipadExportService.exportHelipadsToDatabase(siaExport);
            radioExportService.exportRadiosToDatabase(siaExport);
            serviceExportService.exportServicesToDatabase(siaExport);
            frequencyExportService.exportFrequenciesToDatabase(siaExport);

        } catch (Exception e) {
            logger.error("An error occurred during the process : " + e.getMessage());
            throw new RuntimeException("An error occurred during the process : " + e.getMessage(), e);
        } finally {
            logger.info("XML Parser : FINISHED");
        }
    }
}
