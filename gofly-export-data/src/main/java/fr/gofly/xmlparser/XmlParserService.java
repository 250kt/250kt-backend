package fr.gofly.xmlparser;

import fr.gofly.model.SiaExport;
import fr.gofly.xmlparser.export.*;
import jakarta.annotation.PostConstruct;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import java.io.File;

@Service
@RequiredArgsConstructor
public class XmlParserService {

    private static final String XML_FILE_PATH = "import.xml";
    private final Logger logger = LoggerFactory.getLogger(XmlParserService.class);
    private SiaExport siaExport;

    private final TerritoryExportService territoryExportService;
    private final AirfieldExportService airfieldExportService;
    private final ObstacleExportService obstacleExportService;
    private final HelipadExportService helipadExportService;
    private final RadioExportService radioExportService;
    private final AirspaceExportService airspaceExportService;
    private final FrequencyExportService frequencyExportService;
    private final ServiceExportService serviceExportService;
    private final BorderExportService borderExportService;
    private final PartExportService partExportService;
    private final LighthouseExportService lighthouseExportService;

    /**
     * Parses the XML file and exports objects to the database.
     * This method is automatically executed after the Spring bean is constructed.
     */
    @PostConstruct
    public void parseXMLFile() {
        try {
            logger.info("XML Parser : STARTED");

            File xmlFile = ResourceUtils.getFile("classpath:" + XML_FILE_PATH);

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
            throw new RuntimeException("An error occurred during the process : " + e.getMessage(), e);
        } finally {
            logger.info("XML Parser : FINISHED");
        }
    }
}
