package fr.gofly.model;

import fr.gofly.model.wrapper.AirfieldWrapper;
import fr.gofly.model.wrapper.HelipadWrapper;
import fr.gofly.model.wrapper.ObstacleWrapper;
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

    public AirfieldWrapper getAirfields() {
        return airfields;
    }

    public ObstacleWrapper getObstacles() {
        return obstacles;
    }

    public HelipadWrapper getHelipads() {
        return helipads;
    }
}
