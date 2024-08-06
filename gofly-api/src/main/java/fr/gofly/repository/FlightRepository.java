package fr.gofly.repository;

import fr.gofly.model.Flight;
import fr.gofly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
    @Query("SELECT f FROM Flight f WHERE f.user = ?1 AND f.isCurrentEdit = ?2")
    Optional<Flight> findFirstByUserAndIsCurrentEdit(User user, boolean b);

    @Query("SELECT f FROM Flight f WHERE f.user = ?1 ORDER BY f.isCurrentEdit DESC, f.createdAt DESC")
    Optional<List<Flight>> findAllByUserOrderByCurrentEditAndCreatedAtDesc(User user);

}
