package fr.gofly.repository;

import fr.gofly.model.Taf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TafRepository extends JpaRepository<Taf, Integer> {

    Optional<Taf> findByAirfieldCode(String airfieldCode);
}
