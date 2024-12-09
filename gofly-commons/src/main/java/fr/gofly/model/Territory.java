package fr.gofly.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
    private int id;

    @Column(name = "territory_identification_code")
    private String identificationCode;

    @Column(name = "territory_name")
    @XmlElement(name = "Nom")
    private String name;

    @XmlElement(name = "Territoire")
    public String getIdentificationCode() {
        return identificationCode;
    }

    public void setIdentificationCode(String identificationCode) {
        this.identificationCode = identificationCode;
    }
}
