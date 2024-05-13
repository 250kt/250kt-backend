package fr.gofly.repository;

import fr.gofly.model.airfield.Airfield;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AirfieldRepository extends JpaRepository<Airfield, Long> {
    long countBy();

    @Query("SELECT a FROM Airfield a INNER JOIN Territory t on t.id = a.territory.id WHERE a.isAcceptVfr = true ORDER BY a.code")
    Set<Airfield> findAllByAcceptVfrIsTrue();
}
