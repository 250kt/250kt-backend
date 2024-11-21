package fr.gofly.helper;

import fr.gofly.model.Aircraft;
import fr.gofly.model.Territory;
import fr.gofly.model.airfield.Airfield;
import fr.gofly.model.airfield.AirfieldType;
import fr.gofly.model.flight.Flight;
import fr.gofly.model.flight.FuelMetrics;
import fr.gofly.model.flight.Step;
import fr.gofly.model.flight.StepMetrics;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class FlightHelperTest {

    @InjectMocks
    private FlightHelper flightHelper;

    private static Airfield airfield1;
    private static Airfield airfield2;
    private static final double BASE_FACTOR = 0.6;

    @BeforeAll
    static void setUp() {
        airfield1 = new Airfield(
                1001,
                new Territory(),
                "OW",
                "Active",
                "Test",
                "Test",
                "Test",
                "000",
                true,
                200,
                49.816944f,
                3.206667f,
                AirfieldType.PRIVATE
        );


        airfield2 = new Airfield(
                1001,
                new Territory(),
                "OW",
                "Active",
                "Test",
                "Test",
                "Test",
                "000",
                true,
                200,
                50.960934f,
                1.951433f,
                AirfieldType.PRIVATE
        );
    }

    @Test
    void testCalculateMetricsBetweenTwoPoints() {

        StepMetrics result = flightHelper.calculateMetricsBetweenTwoPoints(airfield1.getLatitude(), airfield1.getLongitude(), airfield2.getLatitude(), airfield2.getLongitude(),  BASE_FACTOR);

        assertEquals(325, result.direction());
        assertEquals(84, result.distance());
        assertEquals(50, result.duration());
    }

    @Test
    void testComputeStepsMetrics_LastStepHasNoMetrics() {
        List<Step> steps = new ArrayList<>();

        Step step1 = new Step();
        step1.setAirfield(airfield1);
        step1.setOrder(0);

        Step step2 = new Step();
        step2.setAirfield(airfield2);
        step2.setOrder(1);

        steps.add(step1);
        steps.add(step2);

        Aircraft aircraft = new Aircraft();
        aircraft.setBaseFactor(BASE_FACTOR);

        Flight currentFlight = new Flight();
        currentFlight.setAircraft(aircraft);

        steps = flightHelper.computeStepsMetrics(steps, currentFlight);

        assertEquals(2, steps.size());
        assertEquals(84, steps.get(0).getDistance());
        assertEquals(0, steps.get(1).getDistance());
    }

    @Test
    void testComputeFuelMetrics() {
        Integer flightDuration = 120;
        Integer aircraftConsumption = 50;

        FuelMetrics result = flightHelper.computeFuelMetrics(flightDuration, aircraftConsumption);

        assertEquals(100.0, result.fuelNeeded());
        assertEquals(10.0, result.fuelSecurityTenPercent());
        assertEquals(25.0, result.fuelReserve());
        assertEquals(135.0, result.fuelOnBoard());
    }

}