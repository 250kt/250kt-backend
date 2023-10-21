package fr.gofly.dto;

import fr.gofly.model.Fuel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AircraftDto {
    private Integer id;
    private String registration;
    private Double baseFactor;
    private Integer consumption;
    private Fuel fuelType;
    private Double leverArmBackSeat;
    private Double leverArmFrontSeat;
    private Double leverArmFuel;
    private Double leverArmLuggage;
    private Double leverArmUnloadedWeight;
    private Integer maximumWeight;
    private Integer nonPumpableFuel;
    private Integer tankCapacity;
    private Integer trueAirSpeed;
    private Integer unloadedWeight;
    private String userId;
}
