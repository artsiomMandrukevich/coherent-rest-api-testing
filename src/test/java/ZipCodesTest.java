import com.coherensolutions.rest.training.dto.response.ZipCodesResponse;
import com.coherensolutions.rest.training.endpoint.TypeListOfZipCodes;
import com.coherensolutions.rest.training.endpoint.ZipCodes;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;

import static com.coherensolutions.rest.training.endpoint.TypeListOfZipCodes.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ZipCodesTest {

    @SneakyThrows
    @Order(1)
    @DisplayName("Scenario #1 from the task 20")
    @Test()
    void zipCodesGetTest() {
        ZipCodes zipCodes = new ZipCodes();
        ZipCodesResponse zipCodesResponse = zipCodes.sendGetZipCodes();
        assertEquals(201, zipCodesResponse.getStatus());
        assertTrue(zipCodesResponse.getZipCodes().containsAll(zipCodes.getListOfZipCodesByType(EXPECTED_AVAILABLE)));
    }

    @SneakyThrows
    @Order(2)
    @DisplayName("Scenario #2 from the task 20")
    @Test()
    void zipCodesPostExpandTest() {
        TypeListOfZipCodes typeListOfZipCodes = EXPANDED;
        ZipCodes zipCodes = new ZipCodes();
        ZipCodesResponse zipCodesResponse = zipCodes.sendPostZipCodes(typeListOfZipCodes);
        assertEquals(201, zipCodesResponse.getStatus());
        assertTrue(zipCodesResponse.getZipCodes().containsAll(zipCodes.getListOfZipCodesByType(typeListOfZipCodes)));
    }

    @SneakyThrows
    @Order(3)
    @DisplayName("Scenario #3 from the task 20")
    @Test()
    void zipCodesPostDuplicationAvailableTest() {
        TypeListOfZipCodes typeListOfZipCodes = DUPLICATED_AVAILABLE;
        ZipCodes zipCodes = new ZipCodes();
        ZipCodesResponse zipCodesResponse = zipCodes.sendPostZipCodes(typeListOfZipCodes);
        assertEquals(201, zipCodesResponse.getStatus());
        assertTrue(zipCodesResponse.getZipCodes().containsAll(zipCodes.getListOfZipCodesByType(typeListOfZipCodes)));
        assertTrue(zipCodes.areThereDuplicateZipCodes(zipCodesResponse.getZipCodes(), zipCodes.getListOfZipCodesByType(typeListOfZipCodes)));
    }

    @SneakyThrows
    @Order(4)
    @DisplayName("Scenario #4 from the task 20")
    @Test()
    void zipCodesPostDuplicationUsedTest() {
        TypeListOfZipCodes typeListOfZipCodes = DUPLICATED_USED;
        ZipCodes zipCodes = new ZipCodes();
        ZipCodesResponse zipCodesResponse = zipCodes.sendPostZipCodes(typeListOfZipCodes);
        assertEquals(201, zipCodesResponse.getStatus());
        assertTrue(zipCodesResponse.getZipCodes().containsAll(zipCodes.getListOfZipCodesByType(typeListOfZipCodes)));
        assertTrue(zipCodes.areThereDuplicateZipCodes(zipCodesResponse.getZipCodes(), zipCodes.getListOfZipCodesByType(typeListOfZipCodes)));
    }

}
