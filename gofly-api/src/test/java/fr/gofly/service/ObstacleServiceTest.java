package fr.gofly.service;

import fr.gofly.dto.ObstacleDto;
import fr.gofly.mapper.ObstacleToObstacleDto;
import fr.gofly.model.obstacle.Obstacle;
import fr.gofly.repository.ObstacleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ObstacleServiceTest {

    @Mock
    private ObstacleRepository obstacleRepository;

    @Mock
    private ObstacleToObstacleDto obstacleMapper;

    @InjectMocks
    private ObstacleService obstacleService;

    private Obstacle obstacle;
    private ObstacleDto obstacleDto;

    @BeforeEach
    void setUp() {
        obstacle = new Obstacle();
        obstacleDto = new ObstacleDto();
    }

    @Test
    void testGetAllObstacles_Found() {
        when(obstacleRepository.findAll()).thenReturn(List.of(obstacle));
        when(obstacleMapper.map(obstacle)).thenReturn(obstacleDto);

        Optional<List<ObstacleDto>> result = obstacleService.getAllObstacles();

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals(obstacleDto, result.get().get(0));
        verify(obstacleRepository).findAll();
        verify(obstacleMapper).map(obstacle);
    }

    @Test
    void testGetAllObstacles_NotFound() {
        when(obstacleRepository.findAll()).thenReturn(Collections.emptyList());

        Optional<List<ObstacleDto>> result = obstacleService.getAllObstacles();

        assertTrue(result.isPresent());
        assertTrue(result.get().isEmpty());
        verify(obstacleRepository).findAll();
        verifyNoInteractions(obstacleMapper);
    }
}