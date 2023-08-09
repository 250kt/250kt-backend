package fr.gofly.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "SiaExport")
@XmlAccessorType(XmlAccessType.FIELD)
public class SiaExport {

    @XmlElement(name = "Situation")
    public Situation situation;

    public List<Airfield> getAirfields() {
        return situation.getAirfields().getAirfields();
    }
}
