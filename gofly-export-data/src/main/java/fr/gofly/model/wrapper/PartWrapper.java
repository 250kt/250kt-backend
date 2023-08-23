package fr.gofly.model.wrapper;

import fr.gofly.model.Part;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "PartieS")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartWrapper{
    @XmlElement(name = "Partie")
    public List<Part> parts;

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

    public List<Part> getParts() {
        return parts;
    }
}