package fr.gofly.repository;

import fr.gofly.model.Aircraft;
import fr.gofly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface AircraftRepository extends JpaRepository<Aircraft, Integer> {

    @Query("SELECT a FROM Aircraft a WHERE a.user = ?1 OR a.isCommon = true")
    Set<Aircraft> findAllByUser(User user);

    void deleteAllByUserId(String userId);

    @Query("SELECT a FROM Aircraft a WHERE a.isFavorite = true AND a.user = ?1")
    Aircraft findByFavoriteTrueAndUser(User user);

    int countByUser(User user);

    @Query("SELECT a FROM Aircraft a WHERE a.isCommon = true")
    Aircraft findDefaultAircraft();
}
