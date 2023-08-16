package fr.gofly.model;

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
    private int serviceId;

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
    private String serviceType;

    @Column(name = "service_indicative_field")
    @XmlElement(name = "IndicLieu")
    private String serviceIndicativeField;

    @Column(name = "service_indicative_service")
    @XmlElement(name = "IndicService")
    private String serviceIndicativeService;

    @Column(name = "service_language")
    @XmlElement(name = "Langue")
    private String serviceLanguage;
}
