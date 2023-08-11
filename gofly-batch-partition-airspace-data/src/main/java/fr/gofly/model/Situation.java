package fr.gofly.model;

import fr.gofly.model.wrapper.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "Situation")
@XmlAccessorType(XmlAccessType.FIELD)
public class Situation {

    @XmlElement(name = "AdS")
    private AirfieldWrapper airfields;

    @XmlElement(name = "ObstacleS")
    private ObstacleWrapper obstacles;

    @XmlElement(name = "HelistationS")
    private HelipadWrapper helipads;

    @XmlElement(name = "RadioNavS")
    private RadioWrapper radios;

    @XmlElement(name = "TerritoireS")
    private TerritoryWrapper territories;

    public AirfieldWrapper getAirfields() {
        return airfields;
    }

    public ObstacleWrapper getObstacles() {
        return obstacles;
    }

    public HelipadWrapper getHelipads() {
        return helipads;
    }

    public RadioWrapper getRadios() {
        return radios;
    }

    public TerritoryWrapper getTerritories() {
        return territories;
    }
}
