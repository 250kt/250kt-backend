package fr.gofly.model.wrapper;

import fr.gofly.model.runway.Runway;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "RwyS")
@XmlAccessorType(XmlAccessType.FIELD)
public class RunwayWrapper {

    @XmlElement(name = "Rwy")
    public List<Runway> runways;

    public List<Runway> getRunways(){
        return runways;
    }
}
