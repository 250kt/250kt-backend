package fr.gofly.xmlparser.export;

import fr.gofly.model.Service;
import fr.gofly.model.SiaExport;
import fr.gofly.repository.ServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@org.springframework.stereotype.Service
public class ServiceExportService {
    private final ServiceRepository serviceRepository;

    private final Logger logger = LoggerFactory.getLogger(ServiceExportService.class);

    public ServiceExportService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    /**
     * Exports services to the database based on the parsed XML data.
     * This method is called internally by parseXMLFile().
     */
    public void exportServicesToDatabase(SiaExport siaExport) {
        try{
            logger.info("Services export : STARTED");
            if (siaExport != null && siaExport.getServices() != null) {
                logger.info("Services export : {} services found", siaExport.getServices().size());

                for (Service service: siaExport.getServices()) {
                    saveServicesToDatabase(service);
                }

                logger.info("Services export : {} services inserted", serviceRepository.countBy());
                if(siaExport.getServices().size() != serviceRepository.countBy()){
                    logger.warn("Services export : Not all services have been exported to the database");
                }
            }else{
                logger.warn("Services export : No services found in the XML file");
            }
        }catch (Exception e){
            throw new RuntimeException("Error during export of services to database: " + e.getMessage(), e);
        }finally {
            logger.info("Services export : FINISHED");
        }
    }

    /**
     * Saves a service to the database.
     * This method is called internally by exportServicesToDatabase().
     *
     * @param service The services object to be saved.
     * @throws RuntimeException If an error occurs during the database operation.
     */
    private void saveServicesToDatabase(Service service) {
        try {
            serviceRepository.save(service);
        } catch (Exception e) {
            throw new RuntimeException("Error saving obstacle to database: " + e.getMessage(), e);
        }
    }
}
