package fr.gofly.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="obstacles")
@RequiredArgsConstructor
@XmlRootElement(name = "ObstacleS")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Obstacle {
    @Id
    @Column(name = "obstacle_id",
            nullable = false)
    @XmlAttribute(name = "pk")
    private int obstacleId;

    @ManyToOne
    @JoinColumn(name = "territory_id")
    @XmlElement(name = "Territoire")
    private Territory territory;

    @Column(name = "obstacle_latitude")
    @XmlElement(name = "Latitude")
    private float obstacleLatitude;

    @Column(name = "obstacle_longitude")
    @XmlElement(name = "Longitude")
    private float obstacleLongitude;

    @Column(name = "obstacle_altitude")
    @XmlElement(name = "AmslFt")
    private float obstacleAltitude;

    @Column(name = "obstacle_height")
    @XmlElement(name = "AglFt")
    private float obstacleHeight;

    @Column(name = "obstacle_type")
    @XmlElement(name = "TypeObst")
    private String obstacleType;

    @Column(name = "obstacle_count")
    @XmlElement(name = "Combien")
    private int obstacleCount;
}
