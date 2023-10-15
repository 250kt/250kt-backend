package fr.gofly.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="helipads")
@RequiredArgsConstructor
@XmlRootElement(name = "Helistation")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Helipad {
    @Id
    @Column(name = "helipad_id")
    @XmlAttribute(name = "pk")
    private int Id;

    @ManyToOne
    @JoinColumn(name = "territory_id")
    @XmlElement(name = "Territoire")
    private Territory territory;

    @Column(name = "helipad_name")
    @XmlElement(name = "Nom")
    private String name;

    @Column(name = "helipad_status")
    @XmlElement(name = "Statut")
    private String status;

    @Column(name = "helipad_altitude")
    @XmlElement(name = "AltitudeFt")
    private int altitude;

    @Column(name = "helipad_latitude")
    @XmlElement(name = "Latitude")
    private float latitude;

    @Column(name = "helipad_longitude")
    @XmlElement(name = "Longitude")
    private float longitude;

    @Column(name = "helipad_operator")
    @XmlElement(name = "Exploitant")
    private String operator;

    @Column(name = "helipad_remark",
            length = 1000)
    @XmlElement(name = "Remarque")
    private String remark;
}
