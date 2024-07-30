package fr.gofly.dto;

import fr.gofly.model.Territory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirfieldShortDto {

    private Integer id;
    private String fullName;
    private String code;
    private Territory territory;
    private Float latitude;
    private Float longitude;
}
