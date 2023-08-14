package fr.gofly.model.wrapper;

import fr.gofly.model.Airfield;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "AdS")
@XmlAccessorType(XmlAccessType.FIELD)
public class AirfieldWrapper {

    @XmlElement(name = "Ad")
    public List<Airfield> airfields;

    public void setAirfields(List<Airfield> airfields) {
        this.airfields = airfields;
    }

    public List<Airfield> getAirfields() {
        return airfields;
    }
}
