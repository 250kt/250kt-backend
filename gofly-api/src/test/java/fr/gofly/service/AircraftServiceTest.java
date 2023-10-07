package fr.gofly.service;

import fr.gofly.model.Aircraft;
import fr.gofly.model.User;
import fr.gofly.repository.AircraftRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AircraftServiceTest {

    @InjectMocks
    private AircraftService aircraftService;

    @Mock
    private AircraftRepository aircraftRepository;

    @Test
    void test_ShouldReturnAircraft_WhenCreateAircraft() {
        Aircraft aircraft = Aircraft.builder().airfieldId(1L).build();
        when(aircraftRepository.save(any(Aircraft.class))).thenReturn(aircraft);
        assertEquals(aircraft, aircraftService.createAircraft(aircraft));
    }

    @Test
    void test_ShouldReturnAircraft_WhenUpdateAircraft() {
        Aircraft aircraft = Aircraft.builder().airfieldId(1L).build();
        when(aircraftRepository.save(any(Aircraft.class))).thenReturn(aircraft);
        assertEquals(aircraft, aircraftService.updateAircraft(aircraft));
    }

    @Test
    void test_ShouldReturnOptionalAircraft_WhenGetAircraft() {
        Aircraft aircraft = Aircraft.builder().airfieldId(1L).build();
        when(aircraftRepository.findById(anyLong())).thenReturn(Optional.of(aircraft));
        assertEquals(Optional.of(aircraft), aircraftService.getAircraft(aircraft.getAirfieldId()));
    }

    @Test
    void test_ShouldReturnOptionalSetOfAircraft_WhenGetUserAircraftList() {

        Aircraft aircraft = Aircraft.builder().airfieldId(1L).build();
        Aircraft aircraft2 = Aircraft.builder().airfieldId(2L).build();
        Set<Aircraft> aircraftSet = new HashSet<>();
        aircraftSet.add(aircraft);
        aircraftSet.add(aircraft2);

        when(aircraftRepository.findAllByUser(any(User.class))).thenReturn(Optional.of(aircraftSet));

        Optional<Set<Aircraft>> aircraftSetTest = aircraftService.getUserAircraftList(new User());

        assertTrue(aircraftSetTest.isPresent());
        assertEquals(aircraftSet, aircraftSetTest.get());
        assertEquals(2, aircraftSetTest.get().size());
    }

}
