package fr.gofly.repository;

import fr.gofly.model.Aircraft;
import fr.gofly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {

    Optional<Set<Aircraft>> findAllByUser(User user);
}
