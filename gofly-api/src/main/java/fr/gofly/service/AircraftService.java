package fr.gofly.service;

import fr.gofly.model.Aircraft;
import fr.gofly.model.User;
import fr.gofly.repository.AircraftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AircraftService {

    private final AircraftRepository aircraftRepository;

    public Aircraft createAircraft(Aircraft aircraft) {
        return aircraftRepository.save(aircraft);
    }

    public Aircraft updateAircraft(Aircraft aircraft) {
        return aircraftRepository.save(aircraft);
    }

    public void deleteAircraft(Aircraft aircraft) {
        aircraftRepository.delete(aircraft);
    }

    public Optional<Aircraft> getAircraft(Long aircraftId) {
        return aircraftRepository.findById(aircraftId);
    }

    public Optional<Set<Aircraft>> getUserAircraftList(User user) {
        return aircraftRepository.findAllByUser(user);
    }

}
