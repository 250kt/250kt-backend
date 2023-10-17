package fr.gofly.controller;

import fr.gofly.dto.AircraftDto;
import fr.gofly.mapper.AircraftToAircraftDto;
import fr.gofly.model.Aircraft;
import fr.gofly.model.User;
import fr.gofly.service.AircraftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/aircraft")
@RequiredArgsConstructor
public class AircraftController {

    private final AircraftService aircraftService;
    private final AircraftToAircraftDto aircraftMapper;

    @GetMapping("/{aircraftId}")
    public ResponseEntity<AircraftDto> retrieveAircraft(@PathVariable Integer aircraftId, @AuthenticationPrincipal User user) {
        Optional<AircraftDto> aircraftOptionalDto = aircraftService.getAircraft(aircraftId, user);
        return aircraftOptionalDto.map(aircraft -> new ResponseEntity<>(aircraft, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Set<AircraftDto>> retrieveAircrafts(@AuthenticationPrincipal User user, @PathVariable String userId) {
        Optional<Set<AircraftDto>> aircraftOptionalDto = aircraftService.getUserAircrafts(userId, user);
        return aircraftOptionalDto.map(aircraft -> new ResponseEntity<>(aircraft, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping
    public ResponseEntity<AircraftDto> createAircraft(@RequestBody Aircraft aircraft, @AuthenticationPrincipal User user) {
        Optional<AircraftDto> aircraftOptionalDto = aircraftService.createAircraft(aircraft, user);
        return aircraftOptionalDto.map(value -> new ResponseEntity<>(aircraftMapper.map(aircraft), HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PutMapping
    public ResponseEntity<AircraftDto> updateAircraft(@RequestBody Aircraft aircraft, @AuthenticationPrincipal User user) {
        Optional<AircraftDto> aircraftOptionalDto = aircraftService.updateAircraft(aircraft, user);
        return aircraftOptionalDto.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/{aircraftId}")
    public ResponseEntity<HttpStatus> deleteAircraft(@PathVariable Integer aircraftId, @AuthenticationPrincipal User user) {
        return aircraftService.deleteAircraft(aircraftId, user) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<AircraftDto>> retrieveAllAircraft() {
        Optional<List<AircraftDto>> aircraftsDto = aircraftService.getAllAircrafts();
        return aircraftsDto.map(aircraft -> new ResponseEntity<>(aircraft, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }
}
