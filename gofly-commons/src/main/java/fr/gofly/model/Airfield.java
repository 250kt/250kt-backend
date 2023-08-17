package fr.gofly.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="airfield")
@RequiredArgsConstructor
@XmlRootElement(name = "Ad")
@XmlAccessorType(XmlAccessType.PROPERTY)
@Getter
public class Airfield {
    @Id
    @Column(name = "airfield_id",
            nullable = false)
    @XmlAttribute(name = "pk")
    private int airfieldId;

    @ManyToOne
    @JoinColumn(name = "territory_id")
    @XmlElement(name = "Territoire")
    private Territory territory;

    @Column(name = "airfield_statut",
            nullable = true)
    @XmlElement(name = "AdStatut")
    private String airfieldStatut;

    @Column(name = "airfield_fullname",
            nullable = true)
    @XmlElement(name = "AdNomComplet")
    private String airfieldFullname;

    @Column(name = "airfield_map_name",
            nullable = true)
    @XmlElement(name = "AdNomCarto")
    private String airfieldMapName;

    @Column(name = "airfield_situation",
            nullable = true)
    @XmlElement(name = "AdSituation")
    private String airfieldSituation;

    @Column(name = "airfield_phone_number",
            nullable = true)
    @XmlElement(name = "AdTel")
    private String airfieldPhoneNumber;

    @Column(name = "airfield_accept_Vfr",
            nullable = true)
    private boolean isAirfieldAcceptVfr;

    @XmlElement(name = "TfcVfr")
    public String getAirfieldAcceptVfrString() {
        return isAirfieldAcceptVfr ? "oui" : "non";
    }

    public void setAirfieldAcceptVfrString(String value) {
        isAirfieldAcceptVfr = "oui".equalsIgnoreCase(value);
    }

    @Column(name = "airfield_altitude",
            nullable = true)
    @XmlElement(name = "AdRefAltFt")
    private int airfieldAltitude;

    @Column(name = "airfield_latitude",
            nullable = true)
    @XmlElement(name = "ArpLat")
    private float airfieldLatitude;

    @Column(name = "airfield_longitude",
            nullable = true)
    @XmlElement(name = "ArpLong")
    private float airfieldLongitude;
}
