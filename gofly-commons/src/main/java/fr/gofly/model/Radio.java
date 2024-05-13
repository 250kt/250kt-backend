package fr.gofly.model;

import fr.gofly.model.airfield.Airfield;
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
    private int id;

    @ManyToOne
    @JoinColumn(name = "airfield_id")
    @XmlElement(name = "Ad")
    private Airfield airfield;

    @Column(name = "radio_frequency")
    @XmlElement(name = "Frequence")
    private String Frequency;

    @Column(name = "radio_phraseo_name")
    @XmlElement(name = "NomPhraseo")
    private String phraseoName;

    @Column(name = "radio_altitude")
    @XmlElement(name = "LatDme")
    private float latitude;

    @Column(name = "radio_longitude")
    @XmlElement(name = "LongDme")
    private float longitude;
}
