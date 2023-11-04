package fr.gofly.repository;

import fr.gofly.dto.AircraftDto;
import fr.gofly.model.Aircraft;
import fr.gofly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface AircraftRepository extends JpaRepository<Aircraft, Integer> {

    //Set<Aircraft> findAllByUser(User user);
    Set<Aircraft> findAllByUserId(String userId);
}
