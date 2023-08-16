package fr.gofly.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="pilot")
@Getter
@Setter
@RequiredArgsConstructor
public class Pilot{

    @Id
    @Column(name = "pilot_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pilotId;

    @Column(name = "pilot_firstname")
    private String pilotFirstName;

    @Column(name = "pilot_lastname")
    private String pilotLastName;

    @Column(name = "pilot_password")
    private String pilotPassword;

    @Column(name = "pilot_email")
    private String pilotEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "pilot_role")
    private Role pilotRole;
}
