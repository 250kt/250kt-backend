package fr.gofly.repository;

import fr.gofly.model.Flight;
import fr.gofly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
    @Query("SELECT f FROM Flight f WHERE f.user = ?1 AND f.isCurrentEdit = ?2")
    Optional<Flight> findFirstByUserAndIsCurrentEdit(User user, boolean b);
}
