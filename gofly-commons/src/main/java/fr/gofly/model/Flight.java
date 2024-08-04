package fr.gofly.model;

import fr.gofly.model.airfield.Airfield;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Flight {

    @Id
    @Column(name = "flight_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_airfield_departure")
    private Airfield airfieldDeparture = null;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_airfield_arrival")
    private Airfield airfieldArrival = null;

    @ManyToOne
    @JoinColumn(name = "flight_user_id", nullable = false)
    private User user;

    @Column(name = "flight_created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_aircraft_id")
    private Aircraft aircraft;

    @Column(name = "flight_is_current_edit")
    private boolean isCurrentEdit;

    @Column(name = "flight_distance")
    private double distance;

    @Column(name = "flight_duration")
    private int duration;

    @Column(name = "flight_direction")
    private int direction;

}
