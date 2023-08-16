package fr.gofly.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="part")
@RequiredArgsConstructor
@XmlRootElement(name = "PartieS")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Part {
    @Id
    @Column(name = "part_id")
    @XmlAttribute(name = "pk")
    private int partId;

    @ManyToOne
    @JoinColumn(name = "airspace_id")
    @XmlElement(name = "Espace")
    private Airspace airspace;

    @Column(name = "part_name",
            nullable = true)
    @XmlElement(name = "NomPartie")
    private String partName;

    @Column(name = "part_number",
            nullable = true)
    @XmlElement(name = "NumeroPartie")
    private int partNumber;

    @Column(name = "part_common_name",
            nullable = true)
    @XmlElement(name = "NomUsuel")
    private String partCommonName;

    @Column(name = "part_geometry",
            length = 10485760)
    @XmlElement(name = "Geometrie")
    private String partGeometry;
}
