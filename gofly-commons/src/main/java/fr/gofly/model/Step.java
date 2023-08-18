package fr.gofly.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stepes")
@SequenceGenerator(name = "sequence_step_generator", sequenceName = "sequence_step", allocationSize = 10)
@Getter
@Setter
@RequiredArgsConstructor
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_step_generator")
    @Column(name = "step_id")
    private long stepId;

    @Column(name = "step_altitude", nullable = false)
    private int stepAltitude;

    @Column(name = "step_cap", nullable = false)
    private String stepCap;

    @Column(name = "step_distance", nullable = false)
    private int stepDistance;

    @Column(name = "step_time", nullable = false)
    private double stepTime;

    @Column(name = "step_order", nullable = false)
    private int stepOrder;

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
