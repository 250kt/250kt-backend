package fr.gofly.model.wrapper;

import fr.gofly.model.Obstacle;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "ObstacleS")
@XmlAccessorType(XmlAccessType.FIELD)
public class ObstacleWrapper{
    @XmlElement(name = "Obstacle")
    public List<Obstacle> obstacles;

    public void setObstacles(List<Obstacle> airfields) {
        this.obstacles = airfields;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }
}
