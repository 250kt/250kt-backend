package fr.gofly.model.wrapper;

import fr.gofly.model.Border;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "BordureS")
@XmlAccessorType(XmlAccessType.FIELD)
public class BorderWrapper {

    @XmlElement(name = "Bordure")
    public List<Border> borders;

    public List<Border> getBorders() {
        return borders;
    }
}
