package fr.gofly.model;

import fr.gofly.model.airfield.Airfield;
import jakarta.persistence.*;
import lombok.Data;

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
