package fr.gofly.model.wrapper;

import fr.gofly.model.Lighthouse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "PhareS")
@XmlAccessorType(XmlAccessType.FIELD)
public class LighthouseWrapper {

    @XmlElement(name = "Phare")
    public List<Lighthouse> lighthouses;

    public List<Lighthouse> getLighthouses() {
        return lighthouses;
    }
}
