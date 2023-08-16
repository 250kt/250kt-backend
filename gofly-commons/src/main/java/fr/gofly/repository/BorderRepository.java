package fr.gofly.repository;

import fr.gofly.model.Border;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface BorderRepository extends JpaRepository<Border, Long> {
    long countBy();
}
