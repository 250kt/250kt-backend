package fr.gofly.model.obstacle;

import fr.gofly.model.Territory;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Entity
@Getter
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
    private float latitude;

    @Column(name = "obstacle_longitude")
    private float longitude;

    @Column(name = "obstacle_altitude")
    @XmlElement(name = "AmslFt")
    private float altitude;

    @Column(name = "obstacle_height")
    private float height;

    @Column(name = "obstacle_type")
    @Enumerated(EnumType.STRING)
    private ObstacleType type;

    @Column(name = "obstacle_beaconing")
    @Enumerated(EnumType.STRING)
    private ObstacleBeaconing beaconing;

    @Column(name = "obstacle_count")
    private int count;

    @Transient
    private String typeObst;

    public void setType(ObstacleType type) {
        this.type = type;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setBeaconing(ObstacleBeaconing beaconing) {
        this.beaconing = beaconing;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setTypeObst(String typeObst) {
        this.typeObst = typeObst;
    }

    @XmlElement(name = "Latitude")
    public float getLatitude() {
        return this.latitude;
    }

    @XmlElement(name = "Longitude")
    public float getLongitude() {
        return this.longitude;
    }

    @XmlElement(name = "AglFt")
    public float getHeight() {
        return this.height;
    }

    @XmlElement(name = "Balisage")
    public ObstacleBeaconing getBeaconing() {
        return this.beaconing;
    }

    @XmlElement(name = "Combien")
    public int getCount() {
        return this.count;
    }

    @XmlElement(name = "TypeObst")
    public String getTypeObst() {
        return this.typeObst;
    }
}
