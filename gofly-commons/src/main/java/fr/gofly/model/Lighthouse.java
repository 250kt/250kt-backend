package fr.gofly.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="lighthouses")
@RequiredArgsConstructor
@XmlRootElement(name = "PhareS")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Lighthouse {
    @Id
    @Column(name = "lighthouse_id",
            nullable = false)
    @XmlAttribute(name = "pk")
    private int id;

    @ManyToOne
    @JoinColumn(name = "territory_id")
    @XmlElement(name = "Territoire")
    private Territory territory;

    @Column(name = "lighhouse_latitude")
    @XmlElement(name = "Latitude")
    private float latitude;

    @Column(name = "lighhouse_longitude")
    @XmlElement(name = "Longitude")
    private float longitude;

    @Column(name = "lighhouse_number_name")
    @XmlElement(name = "NumeroNom")
    private float numberName;

    @Column(name = "lighhouse_type")
    @XmlElement(name = "Type")
    private String type;

    @Column(name = "lighhouse_situation")
    @XmlElement(name = "Situation")
    private String situation;

    @Column(name = "lighhouse_remark",
            length = 1000)
    @XmlElement(name = "Remarque")
    private String remark;
}
