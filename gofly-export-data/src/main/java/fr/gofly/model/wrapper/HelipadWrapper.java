package fr.gofly.model.wrapper;

import fr.gofly.model.Helipad;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "HelistationS")
@XmlAccessorType(XmlAccessType.FIELD)
public class HelipadWrapper {

    @XmlElement(name = "Helistation")
    public List<Helipad> helipads;

    public List<Helipad> getHelipads() {
        return helipads;
    }
}