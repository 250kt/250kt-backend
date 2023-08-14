package fr.gofly.model.wrapper;

import fr.gofly.model.Territory;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;
@XmlRootElement(name = "TerritoireS")
@XmlAccessorType(XmlAccessType.FIELD)
public class TerritoryWrapper{
    @XmlElement(name = "Territoire")
    public List<Territory> territories;

    public void setTerritories(List<Territory> territories) {
        this.territories = territories;
    }

    public List<Territory> getTerritories() {
        return territories;
    }
}