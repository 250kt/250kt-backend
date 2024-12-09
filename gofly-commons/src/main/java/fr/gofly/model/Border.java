package fr.gofly.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="borders")
@RequiredArgsConstructor
@XmlRootElement(name = "Bordure")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Border {
    @Id
    @Column(name = "border_id")
    @XmlAttribute(name = "pk")
    private int id;

    @ManyToOne
    @JoinColumn(name = "territory_id")
    @XmlElement(name = "Territoire")
    private Territory territory;

    @Column(name = "border_code")
    @XmlElement(name = "Code")
    private String code;

    @Column(name = "border_name")
    @XmlElement(name = "Nom")
    private String nom;

    @Column(name = "border_geometry",
            length = 10485760)
    @XmlElement(name = "Geometrie")
    private String geometry;
}