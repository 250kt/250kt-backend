package fr.gofly.service;

import fr.gofly.dto.FlightDto;
import fr.gofly.helper.FlightHelper;
import fr.gofly.mapper.FlightToFlightDto;
import fr.gofly.model.Flight;
import fr.gofly.model.User;
import fr.gofly.model.airfield.Airfield;
import fr.gofly.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

    @Mock
    private FlightHelper flightHelper;

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private FlightToFlightDto flightMapper;

    @InjectMocks
    private FlightService flightService;

    private Flight flight;
    private User user;
    private Airfield airfield;
    private FlightDto flightDto;

    @BeforeEach
    void setUp() {
        flight = new Flight();
        user = new User();
        airfield = new Airfield();
        flightDto = new FlightDto();

        user.setFavoriteAirfield(airfield);
    }

    @Test
    void testCreateFlight_MissingMandatoryField() {
        when(flightHelper.isMissingMandatoryField(flight)).thenReturn(true);

        Optional<FlightDto> result = flightService.createFlight(flight, user);

        assertTrue(result.isEmpty());
        verify(flightHelper).isMissingMandatoryField(flight);
        verifyNoInteractions(flightRepository, flightMapper);
    }

    @Test
    void testCreateFlight_Success() {
        when(flightHelper.isMissingMandatoryField(flight)).thenReturn(false);
        when(flightRepository.save(any(Flight.class))).thenReturn(flight);
        when(flightMapper.map(flight)).thenReturn(flightDto);

        Optional<FlightDto> result = flightService.createFlight(flight, user);

        assertTrue(result.isPresent());
        assertEquals(flightDto, result.get());
        verify(flightHelper).isMissingMandatoryField(flight);
        verify(flightRepository).save(flight);
        verify(flightMapper).map(flight);
    }

    @Test
    void testGetCurrentFlight_NoCurrentFlight() {
        when(flightRepository.findFirstByUserAndIsCurrentEdit(user, true)).thenReturn(Optional.empty());

        Optional<FlightDto> result = flightService.getCurrentFlight(user);

        assertTrue(result.isEmpty());
        verify(flightRepository).findFirstByUserAndIsCurrentEdit(user, true);
        verifyNoInteractions(flightMapper);
    }

    @Test
    void testGetCurrentFlight_Success() {
        when(flightRepository.findFirstByUserAndIsCurrentEdit(user, true)).thenReturn(Optional.of(flight));
        when(flightMapper.map(flight)).thenReturn(flightDto);

        Optional<FlightDto> result = flightService.getCurrentFlight(user);

        assertTrue(result.isPresent());
        assertEquals(flightDto, result.get());
        verify(flightRepository).findFirstByUserAndIsCurrentEdit(user, true);
        verify(flightMapper).map(flight);
    }

    @Test
    void testUpdateFlight_Success() {
        when(flightRepository.save(any(Flight.class))).thenReturn(flight);
        when(flightMapper.map(flight)).thenReturn(flightDto);

        Optional<FlightDto> result = flightService.updateFlight(flight, user);

        assertTrue(result.isPresent());
        assertEquals(flightDto, result.get());
        verify(flightRepository).save(flight);
        verify(flightMapper).map(flight);
    }
}