package fr.gofly.controller;

import fr.gofly.dto.AirspaceDto;
import fr.gofly.dto.ObstacleDto;
import fr.gofly.service.AirspaceService;
import fr.gofly.service.PartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/airspace")
@RequiredArgsConstructor
public class AirspaceController {

    private final AirspaceService airspaceService;
    private final PartService partService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('BUDDING_PILOT')")
    public ResponseEntity<List<AirspaceDto>> retrieveAllAirspace() {
        Optional<List<AirspaceDto>> airspaceDto = airspaceService.getAllAirspace();
        airspaceDto.ifPresent(airspace -> airspace.forEach(airspaceDto1 -> airspaceDto1.setPart(partService.getPartByAirspaceId(airspaceDto1.getId()).orElse(null))));
        return airspaceDto.map(airspace -> new ResponseEntity<>(airspace, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

}
