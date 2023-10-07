package fr.gofly.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "aircrafts")
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Aircraft {
    @Id
    @Column(name = "aircraft_id",
            nullable = false)
    private Long airfieldId;

    @Column(name = "aircraft_registration",
            nullable = false)
    private String aircraftRegistration;

    @Column(name = "aircraft_base_factor",
            nullable = false)
    private int aircraftBaseFactor;

    @Column(name = "aircraft_consumption", nullable = false)
    private int aircraftConsumption;

    @Column(name = "aircraft_fuel_type")
    private Fuel aircraftFuelType;

    @Column(name = "aircraft_lever_arm_back_seat")
    private double aircraftLeverArmBackSeat;

    @Column(name = "aircraft_lever_arm_front_seat")
    private double aircraftLeverArmFrontSeat;

    @Column(name = "aircraft_lever_arm_fuel")
    private double aircraftLeverArmFuel;

    @Column(name = "aircraft_lever_arm_luggage")
    private double aircraftLeverArmLuggage;

    @Column(name = "aircraft_lever_arm_unloaded_weight")
    private double aircraftLeverArmUnloadedWeight;

    @Column(name = "aircraft_maximum_weight")
    private int aircraftMaximumWeight;

    @Column(name = "aircraft_non_pumpable_fuel", nullable = false)
    private int aircraftNonPumpableFuel;

    @Column(name = "aircraft_tank_capacity",
            nullable = false)
    private int aircraftTankCapacity;

    @Column(name = "aircraft_true_air_speed",
            nullable = false)
    private int aircraftTrueAirSpeed;

    @Column(name = "aircraft_unloaded_weight")
    private int aircraftUnloadedWeight;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
