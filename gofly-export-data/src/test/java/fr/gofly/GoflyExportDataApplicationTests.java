package fr.gofly;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {
        GoflyExportData.class,
        H2TestProfileJPAConfig.class})
@ActiveProfiles("test")
class GoflyExportDataApplicationTests {

    @Test
    void contextLoads() {
    }

}
