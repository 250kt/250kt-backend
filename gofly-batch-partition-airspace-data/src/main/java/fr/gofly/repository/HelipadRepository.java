package fr.gofly.repository;

import fr.gofly.model.Helipad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelipadRepository extends JpaRepository<Helipad, Long> {
    long countBy();
}

