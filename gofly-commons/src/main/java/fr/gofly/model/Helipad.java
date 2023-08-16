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
    private int helipadId;

    @ManyToOne
    @JoinColumn(name = "territory_id")
    @XmlElement(name = "Territoire")
    private Territory territory;

    @Column(name = "helipad_name")
    @XmlElement(name = "Nom")
    private String helipadName;

    @Column(name = "helipad_statut")
    @XmlElement(name = "Statut")
    private String helipadStatut;

    @Column(name = "helipad_altitude")
    @XmlElement(name = "AltitudeFt")
    private int helipadAltitude;

    @Column(name = "helipad_latitude")
    @XmlElement(name = "Latitude")
    private float helipadLatitude;

    @Column(name = "helipad_longitude")
    @XmlElement(name = "Longitude")
    private float helipadLongitude;

    @Column(name = "helipad_operator")
    @XmlElement(name = "Exploitant")
    private String helipadOperator;

    @Column(name = "helipad_remark",
            length = 1000)
    @XmlElement(name = "Remarque")
    private String helipadRemark;
}
