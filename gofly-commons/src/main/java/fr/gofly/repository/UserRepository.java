package fr.gofly.repository;

import fr.gofly.model.User;
import fr.gofly.model.airfield.Airfield;
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

    @Query("SELECT u.favoriteAirfield FROM User u WHERE u = :user")
    Optional<Airfield> findFavoriteAirfield(User user);

    Optional<User> findByVerificationCodeAndIsEmailConfirmedFalse(String verificationCode);
}