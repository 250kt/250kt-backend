package fr.gofly.service;

import fr.gofly.dto.PartDto;
import fr.gofly.mapper.PartToPartDto;
import fr.gofly.repository.PartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartService {

    private final PartRepository partRepository;
    private final PartToPartDto partMapper;

    public Optional<List<PartDto>> getPartByAirspaceId(Integer id) {
        return Optional.of(partRepository.findByAirspaceId(id)
                .stream()
                .map(partMapper::map)
                .collect(Collectors.toList()));
    }

}
