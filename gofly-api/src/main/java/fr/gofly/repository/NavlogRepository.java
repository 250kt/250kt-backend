package fr.gofly.repository;

import fr.gofly.model.Navlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface NavlogRepository extends JpaRepository<Navlog, Long> {
    Optional<Navlog> findById(long id);

    Set<Navlog> findAllByUserId(String userId);
}
