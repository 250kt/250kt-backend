package fr.gofly.repository;

import fr.gofly.model.Territory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TerritoryRepository extends JpaRepository<Territory, Long> {
    long countBy();
}