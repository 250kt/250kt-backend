package fr.gofly.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Entity
@Table(name = "navlogs")
public class Navlog {
    @Id
    @Column(name = "navlog_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long navlogId;

    @Column(name = "navlog_back_passenger")
    private int navLogBackPassengerWeight;

    @Column(name = "navlog_front_passenger_weight")
    private int navLogFrontPassengerWeight;

    @Column(name = "navlog_luggage_weight")
    private int navLogLuggageWeight;

    @Column(name = "navlog_embedded_fuel")
    private int navLogEmbeddedFuel;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft;

    @OneToMany(mappedBy = "navlog")
    private List<Step> steps;
}
