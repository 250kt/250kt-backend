package fr.gofly.repository;

import fr.gofly.model.Metar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MetarRepository extends JpaRepository<Metar, Integer> {

    Optional<Metar> findByAirfieldCode(String airfieldCode);
}
