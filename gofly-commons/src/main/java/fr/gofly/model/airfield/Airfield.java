package fr.gofly.model.airfield;

import fr.gofly.model.Territory;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="airfields")
@RequiredArgsConstructor
@XmlRootElement(name = "Ad")
@XmlAccessorType(XmlAccessType.PROPERTY)
@Getter
@AllArgsConstructor
public class Airfield {
    @Id
    @Column(name = "airfield_id",
            nullable = false)
    @XmlAttribute(name = "pk")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "territory_id")
    @XmlElement(name = "Territoire")
    private Territory territory;

    @Column(name = "airfield_code")
    @XmlElement(name = "AdCode")
    private String code;

    @Column(name = "airfield_statut")
    @XmlElement(name = "AdStatut")
    private String status;

    @Column(name = "airfield_fullname")
    @XmlElement(name = "AdNomComplet")
    private String fullName;

    @Column(name = "airfield_map_name")
    @XmlElement(name = "AdNomCarto")
    private String mapName;

    @Column(name = "airfield_situation")
    @XmlElement(name = "AdSituation")
    private String situation;

    @Column(name = "airfield_phone_number")
    @XmlElement(name = "AdTel")
    private String phoneNumber;

    @Column(name = "airfield_accept_Vfr")
    private boolean isAcceptVfr;

    @Column(name = "airfield_altitude")
    @XmlElement(name = "AdRefAltFt")
    private Integer altitude;

    @Column(name = "airfield_latitude")
    @XmlElement(name = "ArpLat")
    private Float latitude;

    @Column(name = "airfield_longitude")
    @XmlElement(name = "ArpLong")
    private Float longitude;

    @Column(name = "airfield_type")
    @Enumerated(EnumType.STRING)
    private AirfieldType type;

    @XmlElement(name = "TfcVfr")
    public String getAcceptVfrString() {
        return isAcceptVfr ? "oui" : "non";
    }

    public void setAcceptVfrString(String value) {
        isAcceptVfr = "oui".equalsIgnoreCase(value);
    }

    public void setType(AirfieldType type) {
        this.type = type;
    }

}
