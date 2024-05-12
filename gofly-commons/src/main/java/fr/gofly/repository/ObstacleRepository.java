package fr.gofly.repository;

import fr.gofly.model.obstacle.Obstacle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObstacleRepository extends JpaRepository<Obstacle, Long> {
    long countBy();
}

