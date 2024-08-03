package fr.gofly.service;

import fr.gofly.dto.AircraftDto;
import fr.gofly.helper.AircraftHelper;
import fr.gofly.helper.UserHelper;
import fr.gofly.mapper.AircraftToAircraftDto;
import fr.gofly.model.Aircraft;
import fr.gofly.model.User;
import fr.gofly.repository.AircraftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AircraftHelper aircraftHelper;
    private final AircraftToAircraftDto aircraftMapper;
    private final UserHelper userHelper;

    public Optional<AircraftDto> createAircraft(Aircraft aircraft, User user) {
        aircraft.setUser(user);
        if (aircraftHelper.isMissingMandatoryField(aircraft)) {
            return Optional.empty();
        }
        aircraft.setBaseFactor(60.0 / (double) aircraft.getTrueAirSpeed());
        return Optional.of(aircraftMapper.map(aircraftRepository.save(aircraft)));
    }

    public Optional<AircraftDto> updateAircraft(Aircraft aircraft, User user) {
        Optional<Aircraft> aircraftDatabase = aircraftRepository.findById(aircraft.getId());

        if (aircraftDatabase.isEmpty() ||
                aircraftHelper.isMissingMandatoryField(aircraft) ||
                !(aircraftHelper.isAircraftOwnedByUser(aircraftDatabase.get(), user) || userHelper.isAdmin(user)))
            return Optional.empty();

        aircraft.setUser(aircraftDatabase.get().getUser());
        aircraft.setBaseFactor(60.0 / (double) aircraft.getTrueAirSpeed());
        return Optional.of(aircraftMapper.map(aircraftRepository.save(aircraft)));
    }

    public boolean deleteAircraft(Integer aircraftId, User user) {
        Optional<Aircraft> aircraftOptional = aircraftRepository.findById(aircraftId);

        if(aircraftOptional.isPresent()){
            if (aircraftHelper.isAircraftOwnedByUser(aircraftOptional.get(), user) || userHelper.isAdmin(user)) {
                aircraftRepository.delete(aircraftOptional.get());
                return true;
            }
        }

        return false;
    }

    public Optional<AircraftDto> getAircraft(Integer aircraftId, User user) {
        Optional<Aircraft> aircraftOptional = aircraftRepository.findById(aircraftId);
        if (aircraftOptional.isPresent() && aircraftHelper.isAircraftOwnedByUser(aircraftOptional.get(), user) || userHelper.isAdmin(user)) {
            if(aircraftOptional.isPresent()){
                return Optional.of(aircraftMapper.map(aircraftOptional.get()));
            }
        }
        return Optional.empty();
    }

    public Optional<Set<AircraftDto>> getUserAircrafts(User user) {
        return Optional.of(aircraftRepository.findAllByUser(user)
                .stream()
                .map(aircraftMapper::map)
                .sorted(Comparator.comparing(AircraftDto::isFavorite).reversed()
                .thenComparing(AircraftDto::getRegistration))
                .collect(Collectors.toCollection(LinkedHashSet::new)));
    }

    public Optional<List<AircraftDto>> getAllAircrafts() {
        return Optional.of(aircraftRepository.findAll()
                .stream()
                .map(aircraftMapper::map)
                .collect(Collectors.toList()));
    }

    public Optional<AircraftDto> changeFavoriteAircraft(Aircraft aircraft, User user) {

        Optional<Aircraft> currentFavoriteAircraft = aircraftRepository.findByFavoriteTrueAndUser(user);

        if(currentFavoriteAircraft.isPresent()){
            currentFavoriteAircraft.get().setFavorite(false);
            aircraftRepository.save(currentFavoriteAircraft.get());
        }
        Optional<Aircraft> newFavoriteAircraft= aircraftRepository.findById(aircraft.getId());

        if (newFavoriteAircraft.isEmpty() || !(aircraftHelper.isAircraftOwnedByUser(newFavoriteAircraft.get(), user))){
            return Optional.empty();
        }

        newFavoriteAircraft.get().setFavorite(!newFavoriteAircraft.get().isFavorite());
        return Optional.of(aircraftMapper.map(aircraftRepository.save(newFavoriteAircraft.get())));
    }
}
