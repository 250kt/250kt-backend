package fr.gofly.repository;

import fr.gofly.model.Airfield;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirfieldRepository extends JpaRepository<Airfield, Long> {

}
