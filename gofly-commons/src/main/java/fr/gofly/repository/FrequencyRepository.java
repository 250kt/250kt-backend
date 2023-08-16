package fr.gofly.repository;

import fr.gofly.model.Frequency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FrequencyRepository extends JpaRepository<Frequency, Long> {
    long countBy();
}

