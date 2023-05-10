package fr.gofly.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BatchFileAirfield {
    private String airfieldId;
    private String airfieldStatut;
    private String airfieldFullname;
    private String airfieldMapName;
    private String airfieldSituation;
    private String airfieldPhone;
    private int airfield_altitude;
    private boolean airfieldAcceptVfr;
    private float latitudeARP;
    private float longitudeARP;
}
