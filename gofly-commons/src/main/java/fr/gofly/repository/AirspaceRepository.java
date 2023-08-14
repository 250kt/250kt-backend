package fr.gofly.repository;

import fr.gofly.model.Airspace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirspaceRepository extends JpaRepository<Airspace, Long> {
    long countBy();
}

