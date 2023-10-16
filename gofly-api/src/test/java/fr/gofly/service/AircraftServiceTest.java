package fr.gofly.service;

import fr.gofly.helper.AircraftHelper;
import fr.gofly.helper.UserHelper;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AircraftServiceTest {

    @InjectMocks
    private AircraftService aircraftService;

    @Mock
    private AircraftRepository aircraftRepository;

    @Mock
    private AircraftHelper aircraftHelper;
    @Mock
    private UserHelper userHelper;

    @Test
    void testCreateAircraft_ShouldReturnOptionalEmpty_WhenAircraftHasOneMissingMandatoryField() {
        User user = new User();
        Aircraft aircraft = Aircraft.builder().id(1).build();

        when(aircraftHelper.isMissingMandatoryField(any(Aircraft.class))).thenReturn(true);

        assertEquals(Optional.empty(), aircraftService.createAircraft(aircraft, user));
    }

    @Test
    void testUpdateAircraft_ShouldReturnOptionalAircraft_WhenAircraftOwnedByUser() {
        User user = new User();
        user.setId("id");

        Aircraft aircraft = Aircraft.builder().id(1).user(user).build();

        when(aircraftHelper.isMissingMandatoryField(any(Aircraft.class))).thenReturn(false);
        when(aircraftHelper.isAircraftOwnedByUser(any(Aircraft.class), any(User.class))).thenReturn(true);
        when(userHelper.isAdmin(any(User.class))).thenReturn(true);
        when(aircraftRepository.save(any(Aircraft.class))).thenReturn(aircraft);

        assertEquals(Optional.of(aircraft), aircraftService.updateAircraft(aircraft, user));
    }

    @Test
    void testUpdateAircraft_ShouldReturnOptionalEmpty_WhenAircraftDoesNotOwnedByUser() {
        User user = new User();
        user.setId("id");
        User user2 = new User();
        user.setId("id2");

        Aircraft aircraft = Aircraft.builder().id(1).user(user2).build();

        when(aircraftHelper.isMissingMandatoryField(any(Aircraft.class))).thenReturn(false);
        when(aircraftHelper.isAircraftOwnedByUser(any(Aircraft.class), any(User.class))).thenReturn(false);

        assertEquals(Optional.empty(), aircraftService.updateAircraft(aircraft, user));
    }

    @Test
    void testGetAircraft_ShouldReturnOptionalAircraft_WhenAircraftOwnedByUser() {
        User user = new User();
        user.setId("id");
        Aircraft aircraft = Aircraft.builder().id(1).build();

        when(aircraftHelper.isAircraftOwnedByUser(any(Aircraft.class), any(User.class))).thenReturn(true);
        when(aircraftRepository.findById(anyInt())).thenReturn(Optional.of(aircraft));

        assertEquals(Optional.of(aircraft), aircraftService.getAircraft(aircraft.getId(), user));
    }

    @Test
    void testGetUserAircrafts_ShouldReturnOptionalSetOfAircraft() {

        Aircraft aircraft = Aircraft.builder().id(1).build();
        Aircraft aircraft2 = Aircraft.builder().id(2).build();
        Set<Aircraft> aircrafts = new HashSet<>();
        aircrafts.add(aircraft);
        aircrafts.add(aircraft2);

        when(aircraftRepository.findAllByUser(any(User.class))).thenReturn(aircrafts);

        assertEquals(2, aircraftService.getUserAircrafts(new User()).size());
    }

    @Test
    void testDeleteAircraft_ShouldReturnTrue_WhenAircraftOwnedByUser() {
        User user = new User();
        user.setId("id");

        Aircraft aircraft = Aircraft.builder().id(1).user(user).build();

        when(aircraftHelper.isAircraftOwnedByUser(any(Aircraft.class), any(User.class))).thenReturn(true);

        assertTrue(aircraftService.deleteAircraft(aircraft, user));

    }

    @Test
    void testDeleteAircraft_ShouldReturnFalse_WhenAircraftDoesNotOwnedByUser() {
        User user = new User();
        user.setId("id");

        Aircraft aircraft = Aircraft.builder().id(1).user(user).build();

        when(aircraftHelper.isAircraftOwnedByUser(any(Aircraft.class), any(User.class))).thenReturn(false);

        assertFalse(aircraftService.deleteAircraft(aircraft, new User()));

    }

}