package fr.gofly.model.wrapper;

import fr.gofly.model.Airfield;
import fr.gofly.model.Airspace;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "EspaceS")
@XmlAccessorType(XmlAccessType.FIELD)
public class AirspaceWrapper {

    @XmlElement(name = "Espace")
    public List<Airspace> airspaces;

    public void setAirspaces(List<Airspace> airspaces) {
        this.airspaces = airspaces;
    }

    public List<Airspace> getAirspaces() {
        return airspaces;
    }
}