package fr.gofly.model.wrapper;

import fr.gofly.model.Obstacle;
import fr.gofly.model.Radio;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "RadioNavS")
@XmlAccessorType(XmlAccessType.FIELD)
public class RadioWrapper{
    @XmlElement(name = "RadioNav")
    public List<Radio> radios;

    public void setRadios(List<Radio> radios) {
        this.radios = radios;
    }

    public List<Radio> getRadios() {
        return radios;
    }
}
