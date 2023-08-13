package fr.gofly.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="obstacle")
@RequiredArgsConstructor
@XmlRootElement(name = "ObstacleS")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Obstacle {
    @Id
    @Column(name = "obstacle_id")
    @XmlAttribute(name = "pk")
    private Long obstacleId;

    @Column(name = "obstacle_latitude",
            nullable = true)
    @XmlElement(name = "Latitude")
    private float obstacleLatitude;

    @Column(name = "obstacle_longitude",
            nullable = true)
    @XmlElement(name = "Longitude")
    private float obstacleLongitude;

    @Column(name = "obstacle_altitude",
            nullable = true)
    @XmlElement(name = "AmslFt")
    private float obstacleAltitude;

    @Column(name = "obstacle_height",
            nullable = true)
    @XmlElement(name = "AglFt")
    private float obstacleHeight;

    @Column(name = "obstacle_type",
            nullable = true)
    @XmlElement(name = "TypeObst")
    private String obstacleType;

    @Column(name = "obstacle_count",
            nullable = true)
    @XmlElement(name = "Combien")
    private int obstacleCount;
}
