package fr.gofly.repository;

import fr.gofly.model.airfield.Airfield;
import fr.gofly.model.runway.Runway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RunwayRepository extends JpaRepository<Runway, Long> {

    List<Runway> getRunwaysByAirfield(Airfield airfield);
}
