package fr.gofly.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="airfield")
@RequiredArgsConstructor
public class Airfield {
    @Id
    @GeneratedValue
    @Getter
    @Column(name="airfield_id")
    private String airfieldId;

    @Getter
    @Column(name="airfield_statut",
            nullable = false)
    private String airfieldStatut;

    @Getter
    @Column(name="airfield_fullname",
            nullable = false)
    private String airfieldFullname;

    @Getter
    @Column(name="airfield_map_name",
            nullable = true)
    private String airfieldMapName;

    @Getter
    @Column(name="airfield_situation",
            nullable = false)
    private String airfieldSituation;

    @Getter
    @Column(name="airfield_phone_number",
            nullable = false)
    private String airfieldPhoneNumber;

    @Getter
    @Column(name="airfield_altitude",
            nullable = false)
    private int airfield_altitude;

    @Getter
    @Column(name="airfield_accept_Vfr",
            nullable = false)
    private boolean airfieldAcceptVfr;

    @Getter
    @Column(name="airfield_latitude_ARP",
            nullable = false)
    private float airfieldLatitudeARP;

    @Getter
    @Column(name="airfield_longitude_ARP",
            nullable = false)
    private float airfieldLongitudeARP;
}
