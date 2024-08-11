package fr.gofly.repository;

import fr.gofly.model.flight.Flight;
import fr.gofly.model.flight.Step;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StepRepository extends JpaRepository<Step, Integer> {
    List<Step> findAllByFlightOrderByOrder(Flight flight);
}
