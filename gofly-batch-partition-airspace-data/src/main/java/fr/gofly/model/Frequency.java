package fr.gofly.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="frequency")
@RequiredArgsConstructor
@XmlRootElement(name = "FrequenceS")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Frequency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "frequency_id")
    private Long frequencyId;

    @Column(name = "frequency_frequency",
            nullable = true)
    @XmlElement(name = "Frequence")
    private float frequency;

    @Column(name = "frequency_operating_hour_code",
            nullable = true)
    @XmlElement(name = "HorCode")
    private String frequencyOperatingHourCode;

    /*@Column(name = "frequency_operating_hour_text",
            nullable = true)
    @XmlElement(name = "HorTxt")
    private String frequencyOperatingHourText;*/

    @Column(name = "frequency_remark",
            nullable = true,
            length = 1000)
    @XmlElement(name = "Remarque")
    private String frequencyRemark;
}
