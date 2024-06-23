package fr.gofly.service;

import fr.gofly.dto.AirspaceDto;
import fr.gofly.mapper.AirspaceToAirspaceDto;
import fr.gofly.repository.AirspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirspaceService {

    private final AirspaceRepository airspaceRepository;
    private final AirspaceToAirspaceDto airspaceMapper;

    public Optional<List<AirspaceDto>> getAllAirspace() {
        return Optional.of(airspaceRepository.findAll()
            .stream()
            .map(airspaceMapper::map)
            .collect(Collectors.toList()));
    }
}
