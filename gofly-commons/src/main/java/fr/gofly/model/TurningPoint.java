package fr.gofly.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "turning_points")
@Getter
@Setter
@RequiredArgsConstructor
public class TurningPoint {
    @Id
    @Column(name = "turning_point_id")
    private long turningPointId;

    @Column(name = "turning_point_estimation_time")
    private int turningPointEstimatedTime;

    @Column(name = "turning_point_real_time")
    private int turningPointRealTime;

    @Column(name = "turning_point_longitude")
    private double turningPointLongitude;

    @Column(name = "turning_point_latitude")
    private double turningPointLatitude;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Step step;

    @ManyToOne
    @JoinColumn(name = "airfield_id")
    private Airfield airfield;
}
