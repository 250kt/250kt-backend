package fr.gofly.controller;

import fr.gofly.dto.AircraftDto;
import fr.gofly.dto.FlightDto;
import fr.gofly.model.Aircraft;
import fr.gofly.model.Flight;
import fr.gofly.model.User;
import fr.gofly.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/flight")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    public ResponseEntity<FlightDto> createFlight(@RequestBody Flight flight, @AuthenticationPrincipal User user) {
        Optional<FlightDto> flightOptionalDto = flightService.createFlight(flight, user);
        return flightOptionalDto.map(flightDto -> new ResponseEntity<>(flightDto, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping
    public ResponseEntity<FlightDto> getFlight(@AuthenticationPrincipal User user) {
        Optional<FlightDto> flightDtoOptional = flightService.getCurrentFlight(user);
        return flightDtoOptional.map(flightDto -> new ResponseEntity<>(flightDto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.OK));
    }

    @PutMapping
    public ResponseEntity<FlightDto> updateFlight(@RequestBody Flight flight, @AuthenticationPrincipal User user) {
        Optional<FlightDto> flightDto = flightService.updateFlight(flight, user);
        return flightDto.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}
