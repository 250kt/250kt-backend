package fr.gofly.repository;

import fr.gofly.model.Airfield;
import fr.gofly.model.Territory;
import fr.gofly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(String userId);
    Optional<User> findByEmail(String userEmail);
    Optional<User> findByUsername(String username);
    void deleteById(String userId);

    @Query("SELECT u.favoriteAirfield FROM User u WHERE u.id = :userId")
    Optional<Airfield> findFavoriteAirfield(String userId);
}