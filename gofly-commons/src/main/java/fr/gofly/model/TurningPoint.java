package fr.gofly.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "turning_points")
public class TurningPoint {
    @Id
    @Column(name = "turning_point_id")
    private long pointId;

    @Column(name = "turning_point_estimation_time")
    private int pointEstimatedTime;

    @Column(name = "turning_point_real_time")
    private int pointRealTime;

    @Column(name = "turning_point_longitude")
    private double pointLongitude;

    @Column(name = "turning_point_latitude")
    private double pointLatitude;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Step step;

    @ManyToOne
    @JoinColumn(name = "airfield_id")
    private Airfield airfield;
}
