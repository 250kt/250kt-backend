package fr.gofly.helper;

import fr.gofly.model.flight.FlightMetrics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FlightHelperTest {

    @InjectMocks
    private FlightHelper flightHelper;

    @Test
    void testCalculateMetricsBetweenTwoPoints() {
        float lat1 = 49.816944f;
        float lon1 = 3.206667f;
        float lat2 = 49.868862f;
        float lon2 = 3.0296f;

        FlightMetrics result = flightHelper.calculateMetricsBetweenTwoPoints(lat1, lon1, lat2, lon2);

        assertEquals(294, result.direction());
        assertEquals(7.53, result.distance());
    }

    @Test
    void testCalculateDuration() {
        int distance = 100;
        Double baseFactor = 0.5;

        int result = flightHelper.calculateDuration(distance, baseFactor);

        assertEquals(50, result);
    }
}