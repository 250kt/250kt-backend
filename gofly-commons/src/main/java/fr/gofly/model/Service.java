package fr.gofly.model;

import fr.gofly.model.airfield.Airfield;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="services")
@RequiredArgsConstructor
@XmlRootElement(name = "Service")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Service {
    @Id
    @Column(name = "service_id")
    @XmlAttribute(name = "pk")
    private int id;

    @ManyToOne
    @JoinColumn(name = "airfield_id")
    @XmlElement(name = "Ad")
    private Airfield airfield;

    @ManyToOne
    @JoinColumn(name = "airspace_id")
    @XmlElement(name = "Espace")
    private Airspace airspace;

    @Column(name = "service_type",
            nullable = true)
    @XmlElement(name = "Service")
    private String type;

    @Column(name = "service_indicative_field")
    @XmlElement(name = "IndicLieu")
    private String indicativeField;

    @Column(name = "service_indicative_service")
    @XmlElement(name = "IndicService")
    private String indicativeService;

    @Column(name = "service_language")
    @XmlElement(name = "Langue")
    private String language;
}
