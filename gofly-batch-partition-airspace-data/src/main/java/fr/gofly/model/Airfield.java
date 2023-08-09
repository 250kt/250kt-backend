package fr.gofly.model;

import fr.gofly.adapter.YesNoToBooleanAdapter;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="airfield")
@RequiredArgsConstructor
@XmlRootElement(name = "Ad")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Airfield {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airfield_id")
    private Long airfieldId;

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
    private int airfield_altitude;

    @Column(name = "airfield_latitude",
            nullable = true)
    @XmlElement(name = "ArpLat")
    private float airfieldLatitude;

    @Column(name = "airfield_longitude",
            nullable = true)
    @XmlElement(name = "ArpLong")
    private float airfieldLongitude;
}
