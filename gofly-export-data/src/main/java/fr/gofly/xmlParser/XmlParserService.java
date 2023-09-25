package fr.gofly.xmlParser;

import fr.gofly.model.SiaExport;
import fr.gofly.xmlParser.export.*;
import jakarta.annotation.PostConstruct;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.ResourceUtils;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

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

            Resource resource = new ClassPathResource("import.xml");
            File tempFile = File.createTempFile("import", ".xml");
            try (InputStream inputStream = resource.getInputStream()) {
                Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            JAXBContext jaxbContext = JAXBContext.newInstance(SiaExport.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            siaExport = (SiaExport) jaxbUnmarshaller.unmarshal(tempFile);

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

            FileSystemUtils.deleteRecursively(tempFile);
        } catch (Exception e) {
            logger.error("An error occurred during the process : " + e.getMessage());
            throw new RuntimeException("An error occurred during the process : " + e.getMessage(), e);
        } finally {
            logger.info("XML Parser : FINISHED");
        }
    }
}
