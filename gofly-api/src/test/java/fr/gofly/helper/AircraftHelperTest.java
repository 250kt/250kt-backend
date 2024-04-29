package fr.gofly.helper;

import fr.gofly.model.Aircraft;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
public class AircraftHelperTest {

    @InjectMocks
    private AircraftHelper aircraftHelper;

    @Test
    void testIsMissingMandatoryField_ShouldReturnTrue_WhenElementMissing() {
        Aircraft aircraft = Aircraft.builder()
                .trueAirSpeed(100)
                .consumption(20)
                .tankCapacity(100)
                .build();
        assertTrue(aircraftHelper.isMissingMandatoryField(aircraft));
    }

    @Test
    void testIsMissingMandatoryField_ShouldReturnFalse_WhenNoElementMissing() {
        Aircraft aircraft = Aircraft.builder()
                .consumption(20)
                .nonPumpableFuel(5)
                .tankCapacity(100)
                .trueAirSpeed(100)
                .build();
        assertFalse(aircraftHelper.isMissingMandatoryField(aircraft));
    }

}
