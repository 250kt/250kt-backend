package fr.gofly.repository;

import fr.gofly.model.Territory;
import fr.gofly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    Optional<User> findByUserEmail(String userEmail);
    void deleteByUserId(String userId);
}