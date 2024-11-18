package fr.gofly.service;

import fr.gofly.dto.AirfieldDto;
import fr.gofly.mapper.AirfieldToAirfieldDto;
import fr.gofly.model.airfield.Airfield;
import fr.gofly.repository.AirfieldRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AirfieldServiceTest {

    @Mock
    private AirfieldRepository airfieldRepository;

    @Mock
    private AirfieldToAirfieldDto airfieldMapper;

    @InjectMocks
    private AirfieldService airfieldService;

    private Airfield airfield;
    private AirfieldDto airfieldDto;

    @BeforeEach
    void setUp() {
        airfield = new Airfield();
        airfieldDto = new AirfieldDto();
    }

    @Test
    void testGetAllAirfieldsAcceptVfr_Found() {
        when(airfieldRepository.findAllByAcceptVfrIsTrue()).thenReturn(Set.of(airfield));
        when(airfieldMapper.map(airfield)).thenReturn(airfieldDto);

        Optional<List<AirfieldDto>> result = airfieldService.getAllAirfieldsAcceptVfr();

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals(airfieldDto, result.get().get(0));
        verify(airfieldRepository).findAllByAcceptVfrIsTrue();
        verify(airfieldMapper).map(airfield);
    }

    @Test
    void testGetAllAirfieldsAcceptVfr_NotFound() {
        when(airfieldRepository.findAllByAcceptVfrIsTrue()).thenReturn(Collections.emptySet());

        Optional<List<AirfieldDto>> result = airfieldService.getAllAirfieldsAcceptVfr();

        assertTrue(result.isPresent());
        assertTrue(result.get().isEmpty());
        verify(airfieldRepository).findAllByAcceptVfrIsTrue();
        verifyNoInteractions(airfieldMapper);
    }

    @Test
    void testGetAllAirfields_Found() {
        when(airfieldRepository.findAll()).thenReturn(List.of(airfield));
        when(airfieldMapper.map(airfield)).thenReturn(airfieldDto);

        Optional<List<AirfieldDto>> result = airfieldService.getAllAirfields();

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals(airfieldDto, result.get().get(0));
        verify(airfieldRepository).findAll();
        verify(airfieldMapper).map(airfield);
    }

    @Test
    void testGetAllAirfields_NotFound() {
        when(airfieldRepository.findAll()).thenReturn(Collections.emptyList());

        Optional<List<AirfieldDto>> result = airfieldService.getAllAirfields();

        assertTrue(result.isPresent());
        assertTrue(result.get().isEmpty());
        verify(airfieldRepository).findAll();
        verifyNoInteractions(airfieldMapper);
    }
}