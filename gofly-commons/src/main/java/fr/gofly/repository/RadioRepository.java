package fr.gofly.repository;

import fr.gofly.model.Radio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RadioRepository extends JpaRepository<Radio, Long> {
    long countBy();
}