package fr.gofly.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "navlogs")
public class Navlog {
    @Id
    @Column(name = "navlog_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "navlog_back_passenger")
    private int backPassengerWeight;

    @Column(name = "navlog_front_passenger_weight")
    private int frontPassengerWeight;

    @Column(name = "navlog_luggage_weight")
    private int luggageWeight;

    @Column(name = "navlog_embedded_fuel")
    private int embeddedFuel;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft;

    @OneToMany(mappedBy = "navlog",
            fetch = FetchType.LAZY)
    private List<Step> steps;
}
