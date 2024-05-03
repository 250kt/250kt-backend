package fr.gofly.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "aircrafts")
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aircraft_id",
            nullable = false)
    private Integer id;

    @Column(name = "aircraft_registration",
            nullable = false)
    private String registration;

    @Column(name = "aircraft_model",
            nullable = false)
    private String model;

    @Column(name = "aircraft_base_factor",
            nullable = false)
    private Double baseFactor;

    @Column(name = "aircraft_consumption", nullable = false)
    private Integer consumption;

    @Column(name = "aircraft_fuel_type")
    private Fuel fuelType;

    @Column(name = "aircraft_lever_arm_back_seat")
    private Double leverArmBackSeat;

    @Column(name = "aircraft_lever_arm_front_seat")
    private Double leverArmFrontSeat;

    @Column(name = "aircraft_lever_arm_fuel")
    private Double leverArmFuel;

    @Column(name = "aircraft_lever_arm_luggage")
    private Double leverArmLuggage;

    @Column(name = "aircraft_lever_arm_unloaded_weight")
    private Double leverArmUnloadedWeight;

    @Column(name = "aircraft_maximum_weight")
    private Integer maximumWeight;

    @Column(name = "aircraft_non_pumpable_fuel", nullable = false)
    private Integer nonPumpableFuel;

    @Column(name = "aircraft_tank_capacity",
            nullable = false)
    private Integer tankCapacity;

    @Column(name = "aircraft_true_air_speed",
            nullable = false)
    private Integer trueAirSpeed;

    @Column(name = "aircraft_unloaded_weight")
    private Integer unloadedWeight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
