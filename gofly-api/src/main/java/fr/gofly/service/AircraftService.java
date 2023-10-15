package fr.gofly.service;

import fr.gofly.helper.AircraftHelper;
import fr.gofly.helper.UserHelper;
import fr.gofly.model.Aircraft;
import fr.gofly.model.User;
import fr.gofly.repository.AircraftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AircraftHelper aircraftHelper;
    private final UserHelper userHelper;

    public Optional<Aircraft> createAircraft(Aircraft aircraft) {
        if (aircraftHelper.isMissingMandatoryField(aircraft)) {
            return Optional.empty();
        }
        return Optional.of(aircraftRepository.save(aircraft));
    }

    public Optional<Aircraft> updateAircraft(Aircraft aircraft, User user) {
        if (aircraftHelper.isMissingMandatoryField(aircraft) || !aircraftHelper.isAircraftOwnedByUser(aircraft, user) || !userHelper.isAdmin(user)){
            return Optional.empty();
        }
        return Optional.of(aircraftRepository.save(aircraft));
    }

    public boolean deleteAircraft(Aircraft aircraft, User user) {
        if (aircraftHelper.isAircraftOwnedByUser(aircraft, user) || userHelper.isAdmin(user)) {
            aircraftRepository.delete(aircraft);
            return true;
        }
        return false;
    }

    public Optional<Aircraft> getAircraft(Integer aircraftId, User user) {
        Optional<Aircraft> aircraftOptional = aircraftRepository.findById(aircraftId);
        if (aircraftOptional.isPresent() && aircraftHelper.isAircraftOwnedByUser(aircraftOptional.get(), user) || userHelper.isAdmin(user)) {
            return aircraftOptional;
        }
        return Optional.empty();
    }

    public Set<Aircraft> getUserAircrafts(User user) {
        return aircraftRepository.findAllByUser(user);
    }

    public List<Aircraft> getAllAircrafts(User user) {
        return aircraftRepository.findAll();
    }

}
