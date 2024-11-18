package fr.gofly.model.flight;

import fr.gofly.model.airfield.Airfield;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "steps")
@SequenceGenerator(name = "sequence_step_generator", sequenceName = "sequence_step", allocationSize = 10)
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_step_generator")
    @Column(name = "step_id")
    private Long id;

    @Column(name = "step_altitude")
    private Integer altitude;

    @Column(name = "step_cap")
    private Integer cap;

    @Column(name = "step_distance")
    private Double distance;

    @Column(name = "step_duration")
    private Integer duration;

    @Column(name = "step_order", nullable = false)
    private Integer order;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @ManyToOne
    @JoinColumn(name = "step_airfield_id", nullable = false)
    private Airfield airfield;

}
