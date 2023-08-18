package fr.gofly.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="airspaces")
@RequiredArgsConstructor
@XmlRootElement(name = "Espace")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Airspace {
    @Id
    @Column(name = "airspace_id")
    @XmlAttribute(name = "pk")
    private int airspaceId;

    @ManyToOne
    @JoinColumn(name = "territory_id")
    @XmlElement(name = "Territoire")
    private Territory territory;

    @Column(name = "airspace_name")
    @XmlElement(name = "Nom")
    private String airspaceName;

    @Column(name = "airspace_type")
    @XmlElement(name = "TypeEspace")
    private String airspaceType;
}
