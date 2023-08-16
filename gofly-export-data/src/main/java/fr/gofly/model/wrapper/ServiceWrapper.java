package fr.gofly.model.wrapper;

import fr.gofly.model.Service;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "ServiceS")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceWrapper{
    @XmlElement(name = "Service")
    public List<Service> services;

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<Service> getServices() {
        return services;
    }
}
