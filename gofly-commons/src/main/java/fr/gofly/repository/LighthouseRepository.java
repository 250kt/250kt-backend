package fr.gofly.repository;

import fr.gofly.model.Helipad;
import fr.gofly.model.Lighthouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LighthouseRepository extends JpaRepository<Lighthouse, Long> {
    long countBy();
}
