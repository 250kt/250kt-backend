package fr.gofly.model.wrapper;

import fr.gofly.model.Frequency;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "FrequenceS")
@XmlAccessorType(XmlAccessType.FIELD)
public class FrequencyWrapper {

    @XmlElement(name = "Frequence")
    public List<Frequency> frequencies;

    public List<Frequency> getFrequencies() {
        return frequencies;
    }
}