package fr.gofly.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="helipad")
@RequiredArgsConstructor
@XmlRootElement(name = "Helistation")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Helipad {
    @Id
    @Column(name = "helipad_id")
    @XmlAttribute(name = "pk")
    private Long helipadId;

    @Column(name = "helipad_name",
            nullable = true)
    @XmlElement(name = "Nom")
    private String helipadName;

    @Column(name = "helipad_statut",
            nullable = true)
    @XmlElement(name = "Statut")
    private String helipadStatut;

    @Column(name = "helipad_altitude",
            nullable = true)
    @XmlElement(name = "AltitudeFt")
    private int helipadAltitude;

    @Column(name = "helipad_latitude",
            nullable = true)
    @XmlElement(name = "Latitude")
    private float helipadLatitude;

    @Column(name = "helipad_longitude",
            nullable = true)
    @XmlElement(name = "Longitude")
    private float helipadLongitude;

    @Column(name = "helipad_operator",
            nullable = true)
    @XmlElement(name = "Exploitant")
    private String helipadOperator;

    @Column(name = "helipad_remark",
            nullable = true,
            length = 1000)
    @XmlElement(name = "Remarque")
    private String helipadRemark;
}
