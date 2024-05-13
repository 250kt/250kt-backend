package fr.gofly.model;

import fr.gofly.model.airfield.Airfield;
import fr.gofly.model.obstacle.Obstacle;
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

    public List<Obstacle> getObstacles() {
        return situation.getObstacles().getObstacles();
    }

    public List<Helipad> getHelipads() {
        return situation.getHelipads().getHelipads();
    }

    public List<Radio> getRadios(){
        return situation.getRadios().getRadios();
    }

    public List<Territory> getTerritories(){
        return situation.getTerritories().getTerritories();
    }

    public List<Airspace> getAirspaces(){
        return situation.getAirspaces().getAirspaces();
    }

    public List<Frequency> getFrequencies(){
        return situation.getFrequencies().getFrequencies();
    }

    public List<Service> getServices(){
        return situation.getServices().getServices();
    }

    public List<Border> getBorders(){
        return situation.getBorders().getBorders();
    }

    public List<Part> getParts(){
        return situation.getParts().getParts();
    }

    public List<Lighthouse> getLighthouses(){
        return situation.getLighthouses().getLighthouses();
    }
}
