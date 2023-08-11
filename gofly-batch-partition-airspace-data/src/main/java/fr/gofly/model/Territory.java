package fr.gofly.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="territory")
@RequiredArgsConstructor
@XmlRootElement(name = "Territoire")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Territory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "territory_id")
    private Long territoryId;

    @Column(name = "territory_identification_code",
            nullable = true)
    @XmlElement(name = "Territoire")
    private String territoryIdentificationCode;

    @Column(name = "territory_name",
            nullable = true)
    @XmlElement(name = "Nom")
    private String territoryName;
}
