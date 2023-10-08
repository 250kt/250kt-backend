package fr.gofly.controller;

import fr.gofly.model.Aircraft;
import fr.gofly.model.User;
import fr.gofly.service.AircraftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/aircraft")
@RequiredArgsConstructor
public class AircraftController {

    private final AircraftService aircraftService;

    @GetMapping("/{aircraftId}")
    public ResponseEntity<Aircraft> retrieveAircraft(@PathVariable Integer aircraftId, @RequestBody User user) {
        Optional<Aircraft> aircraftOptional = aircraftService.getAircraft(aircraftId, user);
        return aircraftOptional.map(aircraft -> new ResponseEntity<>(aircraft, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/")
    public ResponseEntity<Set<Aircraft>> retrieveAircrafts(@RequestBody User user) {
        Set<Aircraft> aircrafts = aircraftService.getUserAircrafts(user);
        return new ResponseEntity<>(aircrafts, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Aircraft> createAircraft(@RequestBody Aircraft aircraft) {
        Optional<Aircraft> aircraftOptional = aircraftService.createAircraft(aircraft);
        return aircraftOptional.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PutMapping("")
    public ResponseEntity<Aircraft> updateAircraft(@RequestBody Aircraft aircraft, @RequestBody User user) {
        Optional<Aircraft> aircraftOptional = aircraftService.updateAircraft(aircraft, user);
        return aircraftOptional.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("")
    public ResponseEntity<Aircraft> deleteAircraft(@RequestBody Aircraft aircraft, @RequestBody User user) {
        return aircraftService.deleteAircraft(aircraft, user) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Aircraft>> retriveAllAircraft(@RequestBody User user) {
        List<Aircraft> aircrafts = aircraftService.getAllAircrafts(user);
        return new ResponseEntity<>(aircrafts, HttpStatus.OK);
    }
}
