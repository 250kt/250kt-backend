package fr.gofly.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "stepes")
@SequenceGenerator(name = "sequence_step_generator", sequenceName = "sequence_step", allocationSize = 10)
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_step_generator")
    @Column(name = "step_id")
    private long id;

    @Column(name = "step_altitude", nullable = false)
    private int altitude;

    @Column(name = "step_cap", nullable = false)
    private String cap;

    @Column(name = "step_distance", nullable = false)
    private int distance;

    @Column(name = "step_time", nullable = false)
    private double time;

    @Column(name = "step_order", nullable = false)
    private int order;

    @ManyToOne
    @JoinColumn(name = "navlog_id", nullable = false)
    private Navlog navlog;

    @OneToOne
    @JoinColumn(name = "report_point_arrived", nullable = false)
    private TurningPoint turningPointArrived;

    @OneToOne
    @JoinColumn(name = "report_point_departure", nullable = false)
    private TurningPoint turningPointDeparture;
}
