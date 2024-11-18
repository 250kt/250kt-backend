package fr.gofly.service;

import fr.gofly.dto.AircraftDto;
import fr.gofly.helper.AircraftHelper;
import fr.gofly.helper.UserHelper;
import fr.gofly.mapper.AircraftToAircraftDto;
import fr.gofly.model.Aircraft;
import fr.gofly.model.Authority;
import fr.gofly.model.User;
import fr.gofly.repository.AircraftRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AircraftServiceTest {

    @InjectMocks
    private AircraftService aircraftService;

    @Mock
    private AircraftRepository aircraftRepository;

    @Mock
    private AircraftHelper aircraftHelper;

    @Mock
    private UserHelper userHelper;

    @Mock
    private AircraftToAircraftDto aircraftMapper;

    private List<Authority> authorities = new ArrayList<>();

    @BeforeEach
    void setUp(){
        authorities.add(Authority.BUDDING_PILOT);
    }

    @Test
    void testCreateAircraft_ShouldReturnOptionalAircraft_WhenAllMandatoryFieldsArePresent() {
        User user = new User();
        Aircraft aircraft = Aircraft.builder().id(1).trueAirSpeed(120).build();
        aircraft.setRegistration("F-GOFL");

        when(aircraftHelper.isMissingMandatoryField(any(Aircraft.class))).thenReturn(false);
        when(aircraftRepository.save(any(Aircraft.class))).thenReturn(aircraft);
        when(aircraftMapper.map(any(Aircraft.class))).thenReturn(AircraftDto.builder().build());

        assertEquals(Optional.of(aircraftMapper.map(aircraft)), aircraftService.createAircraft(aircraft, user));
    }

    @Test
    void testCreateAircraft_ShouldReturnOptionalEmpty_WhenAircraftHasOneMissingMandatoryField() {
        User user = new User();
        Aircraft aircraft = Aircraft.builder().id(1).build();
        aircraft.setRegistration("F-GOFL");

        when(aircraftHelper.isMissingMandatoryField(any(Aircraft.class))).thenReturn(true);

        assertEquals(Optional.empty(), aircraftService.createAircraft(aircraft, user));
    }

    @Test
    void testUpdateAircraft_ShouldReturnOptionalAircraft_WhenAircraftOwnedByUser() {
        User user = new User();
        user.setId("id");
        user.setAuthorities(authorities);

        Aircraft aircraft = Aircraft.builder().id(1).user(user).trueAirSpeed(100).build();
        aircraft.setRegistration("F-gofl");

        when(aircraftRepository.findById(anyInt())).thenReturn(Optional.of(aircraft));
        when(aircraftHelper.isMissingMandatoryField(any(Aircraft.class))).thenReturn(false);
        when(aircraftHelper.isAircraftOwnedByUser(any(Aircraft.class), any(User.class))).thenReturn(true);
        when(aircraftRepository.save(any(Aircraft.class))).thenReturn(aircraft);
        when(aircraftMapper.map(any(Aircraft.class))).thenReturn(AircraftDto.builder().build());

        assertEquals(Optional.of(aircraftMapper.map(aircraft)), aircraftService.updateAircraft(aircraft, user));
    }

    @Test
    void testUpdateAircraft_ShouldReturnOptionalEmpty_WhenAircraftDoesNotOwnedByUser() {
        User user = new User();
        user.setId("id");

        User user2 = User.builder()
            .id("id2")
            .build();

        Aircraft aircraft = Aircraft.builder()
            .id(1)
            .user(user2)
            .build();

        when(aircraftRepository.findById(anyInt())).thenReturn(Optional.of(aircraft));
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
        when(aircraftMapper.map(any(Aircraft.class))).thenReturn(AircraftDto.builder().build());

        assertEquals(Optional.of(aircraftMapper.map(aircraft)), aircraftService.getAircraft(aircraft.getId(), user));
    }

}