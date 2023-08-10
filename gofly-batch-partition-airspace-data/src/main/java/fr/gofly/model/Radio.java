package fr.gofly.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="radio")
@RequiredArgsConstructor
@XmlRootElement(name = "RadioNav")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Radio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "radio_id")
    private Long radioId;

    @Column(name = "radio_frequency",
            nullable = true)
    @XmlElement(name = "Frequence")
    private String radioFrequency;

    @Column(name = "radio_phraseo_name",
            nullable = true)
    @XmlElement(name = "NomPhraseo")
    private String radioPhraseoName;

    @Column(name = "radio_altitude",
            nullable = true)
    @XmlElement(name = "LatDme")
    private float radioLatitude;

    @Column(name = "radio_longitude",
            nullable = true)
    @XmlElement(name = "LongDme")
    private float radioLongitude;
}
