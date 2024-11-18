package fr.gofly.service;

import fr.gofly.dto.AirfieldDto;
import fr.gofly.mapper.AirfieldToAirfieldDto;
import fr.gofly.repository.AirfieldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AirfieldService {

    private final AirfieldRepository airfieldRepository;
    private final AirfieldToAirfieldDto airfieldMapper;

    public Optional<List<AirfieldDto>> getAllAirfieldsAcceptVfr() {
        return Optional.of(airfieldRepository.findAllByAcceptVfrIsTrue()
                .stream()
                .map(airfieldMapper::map)
                .toList());
    }

    public Optional<List<AirfieldDto>> getAllAirfields() {
        return Optional.of(airfieldRepository.findAll()
                .stream()
                .map(airfieldMapper::map)
                .toList());
    }
}
