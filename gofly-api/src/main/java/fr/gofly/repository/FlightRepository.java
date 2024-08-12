package fr.gofly.repository;

import fr.gofly.model.Aircraft;
import fr.gofly.model.flight.Flight;
import fr.gofly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
    @Query("SELECT f FROM Flight f INNER JOIN Step s ON s.flight.id = f.id WHERE f.user = ?1 AND f.isCurrentEdit = ?2 ORDER BY s.order limit 1")
    Optional<Flight> findFirstByUserAndIsCurrentEdit(User user, boolean b);

    @Query("SELECT f FROM Flight f WHERE f.user = ?1 ORDER BY f.isCurrentEdit DESC, f.createdAt DESC")
    Optional<List<Flight>> findAllByUserOrderByCurrentEditAndCreatedAtDesc(User user);

    @Query("SELECT f FROM Flight f WHERE f.user = ?1 AND f.aircraft = ?2")
    List<Flight> findallByUserAndAicraft(User user, Aircraft aircraft);

    @Query("SELECT f FROM Flight f WHERE f.id = ?1 AND f.user = ?2")
    Optional<Flight> findByIdAndUser(Integer id, User user);
}
