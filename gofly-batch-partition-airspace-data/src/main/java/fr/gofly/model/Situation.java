package fr.gofly.model;

import fr.gofly.model.wrapper.AirfieldWrapper;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "Situation")
@XmlAccessorType(XmlAccessType.FIELD)
public class Situation {

    @XmlElement(name = "AdS")
    public AirfieldWrapper airfields;

    public AirfieldWrapper getAirfields() {
        return airfields;
    }

    public void setAirfields(AirfieldWrapper airfields) {
        this.airfields = airfields;
    }
}
