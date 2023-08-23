package fr.gofly.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Table(name="territories")
@RequiredArgsConstructor
@XmlRootElement(name = "Territoire")
@XmlAccessorType(XmlAccessType.PROPERTY)
@Getter
public class Territory {
    @Id
    @Column(name = "territory_id")
    @XmlAttribute(name = "pk")
    private int territoryId;

    @Column(name = "territory_identification_code")
    @XmlElement(name = "Territoire")
    private String territoryIdentificationCode;

    @Column(name = "territory_name",
            nullable = true)
    @XmlElement(name = "Nom")
    private String territoryName;
}
