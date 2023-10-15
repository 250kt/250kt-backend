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
    private int id;

    @ManyToOne
    @JoinColumn(name = "territory_id")
    @XmlElement(name = "Territoire")
    private Territory territory;

    @Column(name = "obstacle_latitude")
    @XmlElement(name = "Latitude")
    private float latitude;

    @Column(name = "obstacle_longitude")
    @XmlElement(name = "Longitude")
    private float longitude;

    @Column(name = "obstacle_altitude")
    @XmlElement(name = "AmslFt")
    private float altitude;

    @Column(name = "obstacle_height")
    @XmlElement(name = "AglFt")
    private float height;

    @Column(name = "obstacle_type")
    @XmlElement(name = "TypeObst")
    private String type;

    @Column(name = "obstacle_count")
    @XmlElement(name = "Combien")
    private int count;
}
