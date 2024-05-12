package fr.gofly.service;

import fr.gofly.dto.ObstacleDto;
import fr.gofly.mapper.ObstacleToObstacleDto;
import fr.gofly.repository.ObstacleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ObstacleService {

    private final ObstacleRepository obstacleRepository;
    private final ObstacleToObstacleDto obstacleMapper;

    public Optional<List<ObstacleDto>> getAllObstacles() {
        return Optional.of(obstacleRepository.findAll()
            .stream()
            .map(obstacleMapper::map)
            .collect(Collectors.toList()));
    }

}
