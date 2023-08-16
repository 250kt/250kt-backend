package fr.gofly.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="radios")
@RequiredArgsConstructor
@XmlRootElement(name = "RadioNav")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Radio {
    @Id
    @Column(name = "radio_id")
    @XmlAttribute(name = "pk")
    private int radioId;

    @ManyToOne
    @JoinColumn(name = "airfield_id")
    @XmlElement(name = "Ad")
    private Airfield airfield;

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
