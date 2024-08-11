package fr.gofly.controller;

import fr.gofly.dto.FlightDto;
import fr.gofly.model.Aircraft;
import fr.gofly.model.flight.Flight;
import fr.gofly.model.User;
import fr.gofly.model.airfield.Airfield;
import fr.gofly.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/flight")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    public ResponseEntity<FlightDto> createFlight(@AuthenticationPrincipal User user) {
        Optional<FlightDto> flightOptionalDto = flightService.createFlight(user);
        return flightOptionalDto.map(flightDto -> new ResponseEntity<>(flightDto, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/current")
    public ResponseEntity<FlightDto> getFlight(@AuthenticationPrincipal User user) {
        Optional<FlightDto> flightDtoOptional = flightService.getCurrentFlight(user);
        return flightDtoOptional.map(flightDto -> new ResponseEntity<>(flightDto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.OK));
    }

    @GetMapping
    public ResponseEntity<List<FlightDto>> getFlights(@AuthenticationPrincipal User user) {
        Optional<List<FlightDto>> flightDtos = flightService.getFlights(user);
        return flightDtos.map(flights -> new ResponseEntity<>(flights, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PutMapping("/current")
    public ResponseEntity<FlightDto> setCurrentFlight(@RequestBody Flight flight, @AuthenticationPrincipal User user) {
        Optional<FlightDto> flightDto = flightService.setCurrentFlight(flight, user);
        return flightDto.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PutMapping("/aircraft")
    public ResponseEntity<FlightDto> setAircraft(@RequestBody Aircraft aircraft, @AuthenticationPrincipal User user) {
        Optional<FlightDto> flightDto = flightService.setAircraft(aircraft, user);
        return flightDto.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PutMapping("/step/{idStep}/airfield")
    public ResponseEntity<FlightDto> setAirfieldStep(@RequestBody Airfield airfield, @PathVariable("idStep") Long idStep, @AuthenticationPrincipal User user) {
        Optional<FlightDto> flightDto = flightService.setAirfieldStep(airfield, idStep, user);
        return flightDto.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/step")
    public ResponseEntity<FlightDto> addStep(@AuthenticationPrincipal User user) {
        Optional<FlightDto> flightDto = flightService.addStep(user);
        return flightDto.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}
