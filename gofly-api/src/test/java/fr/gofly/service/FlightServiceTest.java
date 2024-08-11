package fr.gofly.service;

import fr.gofly.dto.FlightDto;
import fr.gofly.helper.FlightHelper;
import fr.gofly.mapper.FlightToFlightDto;
import fr.gofly.model.Aircraft;
import fr.gofly.model.flight.Flight;
import fr.gofly.model.User;
import fr.gofly.model.airfield.Airfield;
import fr.gofly.repository.FlightRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FlightServiceTest {

    @Mock
    private FlightHelper flightHelper;

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private FlightToFlightDto flightMapper;

    @InjectMocks
    private FlightService flightService;

    @Mock
    private Flight flight;

    @Mock
    private FlightDto flightDto;

    @Mock
    private User user;

    @Mock
    private Airfield airfield;

    @Mock
    private Aircraft aircraft;

    void setUp() {
        when(user.getFavoriteAirfield()).thenReturn(airfield);
        when(airfield.getLatitude()).thenReturn(49.0f);
        when(airfield.getLongitude()).thenReturn(2.0f);
        when(flight.getAircraft()).thenReturn(aircraft);
        when(aircraft.getBaseFactor()).thenReturn(1.0);
    }

    void testGetCurrentFlight_NoCurrentFlight() {
        when(flightRepository.findFirstByUserAndIsCurrentEdit(user, true)).thenReturn(Optional.empty());

        Optional<FlightDto> result = flightService.getCurrentFlight(user);

        assertTrue(result.isEmpty());
        verify(flightRepository).findFirstByUserAndIsCurrentEdit(user, true);
        verifyNoInteractions(flightMapper);
    }

    void testGetCurrentFlight_Success() {
        when(flightRepository.findFirstByUserAndIsCurrentEdit(user, true)).thenReturn(Optional.of(flight));
        when(flightMapper.map(flight)).thenReturn(flightDto);

        Optional<FlightDto> result = flightService.getCurrentFlight(user);

        assertTrue(result.isPresent());
        assertEquals(flightDto, result.get());
        verify(flightRepository).findFirstByUserAndIsCurrentEdit(user, true);
        verify(flightMapper).map(flight);
    }

}