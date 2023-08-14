package fr.gofly.repository;

import fr.gofly.model.Border;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface BorderRepository extends JpaRepository<Border, Long> {
    long countBy();
}
