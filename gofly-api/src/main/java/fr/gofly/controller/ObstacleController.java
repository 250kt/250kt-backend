package fr.gofly.controller;

import fr.gofly.dto.ObstacleDto;
import fr.gofly.service.ObstacleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/obstacle")
@RequiredArgsConstructor
public class ObstacleController {

    private final ObstacleService obstacleService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('BUDDING_PILOT')")
    public ResponseEntity<List<ObstacleDto>> retrieveAllObstacle() {
        Optional<List<ObstacleDto>> obstaclesDto = obstacleService.getAllObstacles();
        return obstaclesDto.map(obstacle -> new ResponseEntity<>(obstacle, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

}
