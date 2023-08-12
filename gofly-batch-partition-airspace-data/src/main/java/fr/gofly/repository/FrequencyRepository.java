package fr.gofly.repository;

import fr.gofly.model.Frequency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FrequencyRepository extends JpaRepository<Frequency, Long> {
    long countBy();
}

