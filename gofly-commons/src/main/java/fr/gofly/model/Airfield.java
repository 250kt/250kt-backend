package fr.gofly.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="airfields")
@RequiredArgsConstructor
@XmlRootElement(name = "Ad")
@XmlAccessorType(XmlAccessType.PROPERTY)
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

    @Column(name = "airfield_code")
    @XmlElement(name = "AdCode")
    private String airfieldCode;

    @Column(name = "airfield_statut")
    @XmlElement(name = "AdStatut")
    private String airfieldStatut;

    @Column(name = "airfield_fullname")
    @XmlElement(name = "AdNomComplet")
    private String airfieldFullname;

    @Column(name = "airfield_map_name")
    @XmlElement(name = "AdNomCarto")
    private String airfieldMapName;

    @Column(name = "airfield_situation")
    @XmlElement(name = "AdSituation")
    private String airfieldSituation;

    @Column(name = "airfield_phone_number")
    @XmlElement(name = "AdTel")
    private String airfieldPhoneNumber;

    @Column(name = "airfield_accept_Vfr")
    private boolean isAirfieldAcceptVfr;

    @XmlElement(name = "TfcVfr")
    public String getAirfieldAcceptVfrString() {
        return isAirfieldAcceptVfr ? "oui" : "non";
    }

    public void setAirfieldAcceptVfrString(String value) {
        isAirfieldAcceptVfr = "oui".equalsIgnoreCase(value);
    }

    @Column(name = "airfield_altitude")
    @XmlElement(name = "AdRefAltFt")
    private int airfieldAltitude;

    @Column(name = "airfield_latitude")
    @XmlElement(name = "ArpLat")
    private float airfieldLatitude;

    @Column(name = "airfield_longitude")
    @XmlElement(name = "ArpLong")
    private float airfieldLongitude;
}