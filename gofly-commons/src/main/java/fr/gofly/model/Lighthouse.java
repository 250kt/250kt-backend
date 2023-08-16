package fr.gofly.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="lighthouse")
@RequiredArgsConstructor
@XmlRootElement(name = "PhareS")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Lighthouse {
    @Id
    @Column(name = "lighthouse_id",
            nullable = false)
    @XmlAttribute(name = "pk")
    private int lighthouseId;

    @ManyToOne
    @JoinColumn(name = "territory_id")
    @XmlElement(name = "Territoire")
    private Territory territory;

    @Column(name = "lighhouse_latitude",
            nullable = true)
    @XmlElement(name = "Latitude")
    private float lighthouseLatitude;

    @Column(name = "lighhouse_longitude",
            nullable = true)
    @XmlElement(name = "Longitude")
    private float lighthouseLongitude;

    @Column(name = "lighhouse_number_name",
            nullable = true)
    @XmlElement(name = "NumeroNom")
    private float lighthouseNumberName;

    @Column(name = "lighhouse_type",
            nullable = true)
    @XmlElement(name = "Type")
    private String lighthouseType;

    @Column(name = "lighhouse_situation",
            nullable = true)
    @XmlElement(name = "Situation")
    private String lighthouseSituation;

    @Column(name = "lighhouse_remark",
            nullable = true,
            length = 1000)
    @XmlElement(name = "Remarque")
    private String lighthouseRemark;
}
