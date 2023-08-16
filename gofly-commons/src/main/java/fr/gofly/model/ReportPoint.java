package fr.gofly.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "report_points")
@Getter
@Setter
@RequiredArgsConstructor
public class ReportPoint {
    @Id
    @Column(name = "report_point_id")
    private long reportPointId;

    @Column(name = "report_point_estimation_time")
    private int reportPointEstimatedTime;

    @Column(name = "report_point_real_time")
    private int reportPointRealTime;

    @Column(name = "report_point_longitude")
    private double reportPointLongitude;

    @Column(name = "report_point_latitude")
    private double reportPointLatitude;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;
}
