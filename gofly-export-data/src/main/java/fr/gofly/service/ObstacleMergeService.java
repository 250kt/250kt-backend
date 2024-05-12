package fr.gofly.service;

import fr.gofly.model.obstacle.Obstacle;
import fr.gofly.model.obstacle.ObstacleBeaconing;
import fr.gofly.model.obstacle.ObstacleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ObstacleMergeService {

    private final static double distanceMax = 0.9;

    public List<Obstacle> mergeObstacles(List<Obstacle> obstacles) {
        List<List<Obstacle>> groupsLocals = findLocalGroups(obstacles);
        List<Obstacle> obstaclesMerge = new ArrayList<>();

        for (List<Obstacle> group : groupsLocals) {
            Obstacle obstacleCentral = chooseCentralObstacle(group);
            obstaclesMerge.add(obstacleCentral);
        }

        return obstaclesMerge;
    }

    private List<List<Obstacle>> findLocalGroups(List<Obstacle> obstacles) {
        List<List<Obstacle>> groupsLocals = new ArrayList<>();
        boolean[] obstacleViews = new boolean[obstacles.size()];

        for (int i = 0; i < obstacles.size(); i++) {
            if (!obstacleViews[i]) {
                Obstacle currentObstacle = obstacles.get(i);
                List<Obstacle> group = new ArrayList<>();
                group.add(currentObstacle);
                obstacleViews[i] = true;

                for (int j = i + 1; j < obstacles.size(); j++) {
                    if (!obstacleViews[j]) {
                        Obstacle nextObstacle = obstacles.get(j);
                        if (distanceBetween(currentObstacle, nextObstacle) <= distanceMax) {
                            group.add(nextObstacle);
                            obstacleViews[j] = true;
                        }
                    }
                }

                groupsLocals.add(group);
            }
        }

        return groupsLocals;
    }

    private Obstacle chooseCentralObstacle(List<Obstacle> group) {
        float sumLatitude = 0.0F;
        float sumLongitude = 0.0F;
        float maxHeight = 0.0F;
        Obstacle obstacleCentral = new Obstacle();

        for (Obstacle obstacle : group) {
            sumLatitude += obstacle.getLatitude();
            sumLongitude += obstacle.getLongitude();
            if (obstacle.getHeight() > maxHeight) {
                maxHeight = obstacle.getHeight();
                obstacleCentral = obstacle;
            }
        }

        float centerLatitude = sumLatitude / group.size();
        float centerLongitude = sumLongitude / group.size();

        obstacleCentral.setLatitude(centerLatitude);
        obstacleCentral.setLongitude(centerLongitude);
        obstacleCentral.setHeight(maxHeight);

        return obstacleCentral;
    }

    private double distanceBetween(Obstacle obstacle1, Obstacle obstacle2) {
        final int EARTH_RADIUS = 6371;

        double lat1 = Math.toRadians(obstacle1.getLatitude());
        double lon1 = Math.toRadians(obstacle1.getLongitude());
        double lat2 = Math.toRadians(obstacle2.getLatitude());
        double lon2 = Math.toRadians(obstacle2.getLongitude());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

}
