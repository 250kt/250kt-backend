package fr.gofly.model.flight;

import fr.gofly.model.Aircraft;
import fr.gofly.model.User;
import fr.gofly.model.airfield.Airfield;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "flight_user_id", nullable = false)
    private User user;

    @Column(name = "flight_created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "flight_aircraft_id")
    private Aircraft aircraft;

    @Column(name = "flight_is_current_edit")
    private Boolean isCurrentEdit;

    @Column(name = "flight_distance")
    private Double distance;

    @Column(name = "flight_duration")
    private Integer duration;

    @OneToOne(mappedBy = "flight", cascade = CascadeType.ALL)
    private FuelReport fuelReport;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<Step> steps;
}
