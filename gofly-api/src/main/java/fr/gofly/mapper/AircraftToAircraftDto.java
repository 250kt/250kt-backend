package fr.gofly.mapper;

import fr.gofly.dto.AircraftDto;
import fr.gofly.model.Aircraft;
import org.springframework.stereotype.Component;

@Component
public class AircraftToAircraftDto {

    public AircraftDto map(Aircraft aircraft){
        return AircraftDto.builder()
            .id(aircraft.getId())
            .baseFactor(aircraft.getBaseFactor())
            .consumption(aircraft.getConsumption())
            .fuelType(aircraft.getFuelType())
            .leverArmBackSeat(aircraft.getLeverArmBackSeat())
            .leverArmFrontSeat(aircraft.getLeverArmFrontSeat())
            .leverArmFuel(aircraft.getLeverArmFuel())
            .leverArmLuggage(aircraft.getLeverArmLuggage())
            .nonPumpableFuel(aircraft.getNonPumpableFuel())
            .registration(aircraft.getRegistration())
            .tankCapacity(aircraft.getTankCapacity())
            .trueAirSpeed(aircraft.getTrueAirSpeed())
            .unloadedWeight(aircraft.getUnloadedWeight())
            .leverArmUnloadedWeight(aircraft.getLeverArmUnloadedWeight())
            .maximumWeight(aircraft.getMaximumWeight())
            .userId(aircraft.getUser().getId())
            .build();
    }
}
