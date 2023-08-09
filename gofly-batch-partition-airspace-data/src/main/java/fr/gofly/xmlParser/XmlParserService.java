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
    /*public static List<Airfield> parseXml(InputStream xmlInputStream) throws Exception {
        List<Airfield> airfields = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlInputStream);

        NodeList adNodes = document.getElementsByTagName("Ad");
        for (int i = 0; i < adNodes.getLength(); i++) {
            Element airfieldElement = (Element) adNodes.item(i);
            Airfield ad = createAdFromElement(airfieldElement);
            airfields.add(ad);
        }

        return airfields;
    }

    private static Airfield createAdFromElement(Element airfieldElement) {
        Airfield airfield = new Airfield();

        airfield.setAirfieldStatut(getElementValue(airfieldElement, "AdStatut"));
        airfield.setAirfieldFullname(getElementValue(airfieldElement, "AdNomComplet"));
        airfield.setAirfieldMapName(getElementValue(airfieldElement, "AdNomCarto"));
        // Set other fields here

        return airfield;
    }

    private static String getElementValue(Element parentElement, String elementName) {
        NodeList nodeList = parentElement.getElementsByTagName(elementName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }*/

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

                for (Airfield airfield: siaExport.getAirfields()) {
                    saveAirfieldToDatabase(airfield);
                }
            }else{
                logger.info("Airfields export : No airfields found in the XML file.");
            }
        }catch (Exception e){
            logger.error("Error during export of Airfield to database: " + e.getMessage());
            throw new RuntimeException("Error during export of Airfield to database: " + e.getMessage(), e);
        }finally {
            logger.info("Airfields export : FINISHED");
        }
    }


    /*public void exportAirfieldsToDatabase(){
        System.out.println("Export airfields : STARTED");
        List<Airfield> airfields;

        try {
            airfields = getAirfields();

            if (airfields != null && airfields.size() > 0) {
                for (Airfield airfield: airfields) {
                    saveAirfieldToDatabase(airfield);
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        } finally {
            System.out.println("Export airfields : FINISHED");
        }
    }*/

    private void saveAirfieldToDatabase(Airfield airfield) {
        try {
            airfieldRepository.save(airfield);
        } catch (Exception e) {
            logger.error("Error saving Airfield to database: " + e.getMessage());
            throw new RuntimeException("Error saving Airfield to database: " + e.getMessage(), e);
        }
    }

    /*private List<Airfield> getAirfields() {
        File xmlFile;
        JAXBContext jaxbContext;

        try {
            xmlFile = ResourceUtils.getFile("classpath:" + xmlFilePath);

            jaxbContext = JAXBContext.newInstance(AirfieldWrapper.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            AirfieldWrapper airfieldWrapper = (AirfieldWrapper) jaxbUnmarshaller.unmarshal(xmlFile);

            if (airfieldWrapper != null && airfieldWrapper.getAirfields() != null) {
                return airfieldWrapper.getAirfields();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }*/
}
