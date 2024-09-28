package fr.gofly.service;

import fr.gofly.dto.AircraftDto;
import fr.gofly.dto.FlightDto;
import fr.gofly.helper.AircraftHelper;
import fr.gofly.helper.FlightHelper;
import fr.gofly.helper.UserHelper;
import fr.gofly.mapper.AircraftToAircraftDto;
import fr.gofly.model.Aircraft;
import fr.gofly.model.User;
import fr.gofly.model.flight.Flight;
import fr.gofly.model.flight.Step;
import fr.gofly.repository.AircraftRepository;
import fr.gofly.repository.FlightRepository;
import fr.gofly.repository.StepRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AircraftHelper aircraftHelper;
    private final AircraftToAircraftDto aircraftMapper;
    private final UserHelper userHelper;
    private final FlightRepository flightRepository;
    private final StepRepository stepRepository;
    private final FlightHelper flightHelper;


    public Optional<AircraftDto> createAircraft(Aircraft aircraft, User user) {
        if(aircraftRepository.countByUser(user)<1){
            aircraft.setFavorite(true);
        }

        aircraft.setRegistration(aircraft.getRegistration().toUpperCase());
        aircraft.setUser(user);
        if (aircraftHelper.isMissingMandatoryField(aircraft)) {
            return Optional.empty();
        }
        aircraft.setBaseFactor(Math.round((60.0 / (double) aircraft.getTrueAirSpeed()) * 100.0) / 100.0);
        return Optional.of(aircraftMapper.map(aircraftRepository.save(aircraft)));
    }

    public Optional<AircraftDto> updateAircraft(Aircraft aircraft, User user) {
        Optional<Aircraft> aircraftDatabase = aircraftRepository.findById(aircraft.getId());

        if (aircraftDatabase.isEmpty() ||
                aircraftHelper.isMissingMandatoryField(aircraft) ||
                !(aircraftHelper.isAircraftOwnedByUser(aircraftDatabase.get(), user) || userHelper.isAdmin(user)))
            return Optional.empty();

        aircraft.setRegistration(aircraft.getRegistration().toUpperCase());
        aircraft.setUser(aircraftDatabase.get().getUser());
        aircraft.setBaseFactor(Math.round((60.0 / (double) aircraft.getTrueAirSpeed()) * 100.0) / 100.0);
        return Optional.of(aircraftMapper.map(aircraftRepository.save(aircraft)));
    }

    public boolean deleteAircraft(Integer aircraftId, User user) {
        Optional<Aircraft> aircraftOptional = aircraftRepository.findById(aircraftId);

        if(aircraftOptional.isEmpty()) {
            return false;
        }

        setDefaultAircraft(aircraftOptional.get(), user);

        if (aircraftHelper.isAircraftOwnedByUser(aircraftOptional.get(), user) || userHelper.isAdmin(user)) {
            aircraftRepository.delete(aircraftOptional.get());
            return true;
        }

        return false;
    }


    private void setDefaultAircraft(Aircraft aircraft, User user) {
        List<Flight> flights =  flightRepository.findallByUserAndAicraft(user, aircraft);
        Aircraft defaultAircraft = aircraftRepository.findDefaultAircraft();

        flights.forEach(flight -> {
            flight.setAircraft(defaultAircraft);
            flightRepository.save(flight);
            List<Step> steps = stepRepository.findAllByFlightOrderByOrder(flight);
            stepRepository.saveAll(flightHelper.computeStepsMetrics(steps, flight));
            flightRepository.save(flightHelper.computeTotalMetrics(flight));
        });
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
                .sorted(Comparator.comparing(AircraftDto::isCommon))
                .collect(Collectors.toCollection(LinkedHashSet::new)));
    }

    public Optional<List<AircraftDto>> getAllAircrafts() {
        return Optional.of(aircraftRepository.findAll()
                .stream()
                .map(aircraftMapper::map)
                .collect(Collectors.toList()));
    }

    public Optional<AircraftDto> changeFavoriteAircraft(Aircraft aircraft, User user) {

        Aircraft currentFavoriteAircraft = aircraftRepository.findByFavoriteTrueAndUser(user);
        Optional<Aircraft> newFavoriteAircraft= aircraftRepository.findById(aircraft.getId());

        if (newFavoriteAircraft.isEmpty() || !(aircraftHelper.isAircraftOwnedByUser(newFavoriteAircraft.get(), user))){
            return Optional.empty();
        }

        currentFavoriteAircraft.setFavorite(false);
        aircraftRepository.save(currentFavoriteAircraft);

        newFavoriteAircraft.get().setFavorite(!newFavoriteAircraft.get().isFavorite());
        return Optional.of(aircraftMapper.map(aircraftRepository.save(newFavoriteAircraft.get())));
    }
}
