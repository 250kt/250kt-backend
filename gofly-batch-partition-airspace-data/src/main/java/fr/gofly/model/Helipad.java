package fr.gofly.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="helipad")
@RequiredArgsConstructor
@XmlRootElement(name = "Helistation")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Helipad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "helipad_id")
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
    private float helipadOperator;

    @Column(name = "helipad_remark",
            nullable = true)
    @XmlElement(name = "Remarque")
    private float helipadRemark;
}
