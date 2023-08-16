package fr.gofly.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "branches")
@SequenceGenerator(name = "sequence_branch_generator", sequenceName = "sequence_branch", allocationSize = 10)
@Getter
@Setter
@RequiredArgsConstructor
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_branch_generator")
    @Column(name = "branch_id")
    private long branchId;

    @Column(name = "branch_altitude", nullable = false)
    private int branchAltitude;

    @Column(name = "branch_cap", nullable = false)
    private String branchCap;

    @Column(name = "branch_distance", nullable = false)
    private int branchDistance;

    @Column(name = "branch_time", nullable = false)
    private int branchTime;

    @Column(name = "branch_order", nullable = false)
    private int branchOrder;

    @ManyToOne
    @JoinColumn(name = "navlog_id", nullable = false)
    private Navlog navlog;

    @OneToOne
    @JoinColumn(name = "report_point_arrived", nullable = false)
    private ReportPoint reportPointArrived;

    @OneToOne
    @JoinColumn(name = "report_point_departure", nullable = false)
    private ReportPoint reportPointDeparture;
}
