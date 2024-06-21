package fr.gofly.repository;

import fr.gofly.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
    long countBy();

    List<Part> findByAirspaceId(Integer id);
}

