package fr.gofly.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="frequencies")
@RequiredArgsConstructor
@XmlRootElement(name = "FrequenceS")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Frequency {
    @Id
    @Column(name = "frequency_id")
    @XmlAttribute(name = "pk")
    private int id;

    @ManyToOne
    @JoinColumn(name = "service_id")
    @XmlElement(name = "Service")
    private Service service;

    @Column(name = "frequency_frequency")
    @XmlElement(name = "Frequence")
    private float frequency;

    @Column(name = "frequency_operating_hour_code")
    @XmlElement(name = "HorCode")
    private String operatingHourCode;

    @Column(name = "frequency_remark",
            length = 1000)
    @XmlElement(name = "Remarque")
    private String remark;
}
