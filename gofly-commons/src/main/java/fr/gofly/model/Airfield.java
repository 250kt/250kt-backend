package fr.gofly.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="airfield")
@RequiredArgsConstructor
@XmlRootElement(name = "Ad")
public class Airfield {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airfield_id")
    private String airfieldId;

    @Getter
    @Column(name = "airfield_statut",
            nullable = false)
    @XmlElement(name = "AdStatut")
    private String airfieldStatut;

    @Getter
    @Column(name = "airfield_fullname",
            nullable = false)
    @XmlElement(name = "AdNomComplet")
    private String airfieldFullname;

    @Getter
    @Column(name = "airfield_map_name",
            nullable = true)
    @XmlElement(name = "AdNomCarto")
    private String airfieldMapName;

    @Getter
    @Column(name = "airfield_situation",
            nullable = false)
    @XmlElement(name = "AdSituation")
    private String airfieldSituation;

    @Getter
    @Column(name = "airfield_phone_number",
            nullable = false)
    @XmlElement(name = "AdTel")
    private String airfieldPhoneNumber;

    @Getter
    @Column(name = "airfield_accept_Vfr",
            nullable = false)
    @XmlElement(name = "TfcVfr")
    private boolean airfieldAcceptVfr;

    @Getter
    @Column(name = "airfield_altitude",
            nullable = false)
    @XmlElement(name = "AdRefAltFt")
    private int airfield_altitude;

    @Getter
    @Column(name = "airfield_latitude",
            nullable = false)
    @XmlElement(name = "ArpLat")
    private float airfieldLatitude;

    @Getter
    @Column(name = "airfield_longitude",
            nullable = false)
    @XmlElement(name = "ArpLong")
    private float airfieldLongitude;
}
