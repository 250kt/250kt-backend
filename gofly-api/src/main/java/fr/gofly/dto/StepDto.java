package fr.gofly.dto;

import fr.gofly.model.airfield.Airfield;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class StepDto {

    private Long id;
    @Nullable
    private Integer altitude;
    private Integer cap;
    private Double distance;
    private Integer duration;
    private Integer order;
    private AirfieldDto airfield;
}
