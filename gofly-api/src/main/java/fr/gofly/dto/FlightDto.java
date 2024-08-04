package fr.gofly.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class FlightDto {

    private Long id;
    @Nullable
    private AirfieldDto airfieldDeparture;
    @Nullable
    private AirfieldDto airfieldArrival;
    private String createdAt;
    private AircraftDto aircraft;
    private boolean isCurrentEdit;
    private double distance;
    private int duration;
    private int direction;

}
