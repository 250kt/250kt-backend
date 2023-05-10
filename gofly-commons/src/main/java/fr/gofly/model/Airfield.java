package fr.gofly.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="airfield")
@RequiredArgsConstructor
public class Airfield {
    @Id
    @GeneratedValue
    @Column(name="airfield_id")
    private String airfieldId;

    @Column(name="airfield_statut",
            nullable = false)
    private String airfieldStatut;

    @Column(name="airfield_fullname",
            nullable = false)
    private String airfieldFullname;

    @Column(name="airfield_map_name",
            nullable = true)
    private String airfieldMapName;

    @Column(name="airfield_situation",
            nullable = false)
    private String airfieldSituation;

    @Column(name="airfield_phone",
            nullable = false)
    private String airfieldPhone;

    @Column(name="airfield_altitude",
            nullable = false)
    private int airfield_altitude;

    @Column(name="airfield_accept_Vfr",
            nullable = false)
    private boolean airfieldAcceptVfr;

    @Column(name="latitude_ARP",
            nullable = false)
    private float latitudeARP;

    @Column(name="longitude_ARP",
            nullable = false)
    private float longitudeARP;
}
