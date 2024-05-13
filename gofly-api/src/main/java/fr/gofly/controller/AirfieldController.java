package fr.gofly.controller;

import fr.gofly.dto.AirfieldDto;
import fr.gofly.service.AirfieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/airfield")
@RequiredArgsConstructor
public class AirfieldController {

    private final AirfieldService airfieldService;

    @GetMapping("/all-accept-vfr")
    public ResponseEntity<List<AirfieldDto>> retrieveAllAirfieldsAcceptVfr() {
        Optional<List<AirfieldDto>> airfieldsDto = airfieldService.getAllAirfieldsAcceptVfr();
        return airfieldsDto.map(airfield -> new ResponseEntity<>(airfield, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AirfieldDto>> retrieveAllAirfields() {
        Optional<List<AirfieldDto>> airfieldsDto = airfieldService.getAllAirfields();
        return airfieldsDto.map(airfield -> new ResponseEntity<>(airfield, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

}
