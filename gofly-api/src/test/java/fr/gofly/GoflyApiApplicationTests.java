package fr.gofly;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {
        GoflyApiApplication.class,
        H2TestProfileJPAConfig.class})
@ActiveProfiles("test")
class GoflyExportDataApplicationTests {

    @Test
    void contextLoads() {
    }

}
