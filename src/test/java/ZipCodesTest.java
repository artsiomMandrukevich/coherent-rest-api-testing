import com.coherensolutions.rest.training.dto.response.ZipCodesResponse;
import com.coherensolutions.rest.training.http.ClientZipCodes;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ZipCodesTest {

    @Order(1)
    @DisplayName("Scenario #1 from the task 20. Get all available zip codes in the application for now.")
    @Test()
    void zipCodesGetTest() {
        List<String> expectedZipCodes = new ArrayList<>(Arrays.asList("12345" ,"23456", "ABCDE"));
        ClientZipCodes clientZipCodes = new ClientZipCodes();
        ZipCodesResponse zipCodesResponse = clientZipCodes.sendGetZipCodes(201);
        assertTrue(zipCodesResponse.getZipCodes().containsAll(expectedZipCodes));
    }

    @Order(2)
    @DisplayName("Scenario #2 from the task 20. Zip codes from request body are added to available zip codes of application.")
    @Test()
    void zipCodesPostExpandTest() {
        List<String> expectedZipCodes = new ArrayList<>(Arrays.asList("22201" ,"22202"));
        ClientZipCodes clientZipCodes = new ClientZipCodes();
        ZipCodesResponse zipCodesResponse = clientZipCodes.sendPostZipCodes(expectedZipCodes, 201);
        assertTrue(zipCodesResponse.getZipCodes().containsAll(expectedZipCodes));
    }

    @Order(3)
    @DisplayName("Scenario #3 from the task 20. Zip codes from request body are added to available zip codes of application. There are no duplications in available zip codes")
    @Test()
    void zipCodesPostDuplicationAvailableTest() {
        List<String> expectedZipCodes = new ArrayList<>(Arrays.asList("44401", "44402", "12345"));
        ClientZipCodes clientZipCodes = new ClientZipCodes();
        ZipCodesResponse zipCodesResponse = clientZipCodes.sendPostZipCodes(expectedZipCodes, 201);
        assertTrue(zipCodesResponse.getZipCodes().containsAll(expectedZipCodes));
        assertThat(zipCodesResponse.getZipCodes()).doesNotHaveDuplicates();
    }

    @Order(4)
    @DisplayName("Scenario #4 from the task 20. Zip codes from request body are added to available zip codes of application. There are no duplications between available zip codes and already used zip code")
    @Test()
    void zipCodesPostDuplicationUsedTest() {
        List<String> expectedZipCodes = new ArrayList<>(Arrays.asList("33301", "33302", "33302"));
        ClientZipCodes clientZipCodes = new ClientZipCodes();
        ZipCodesResponse zipCodesResponse = clientZipCodes.sendPostZipCodes(expectedZipCodes, 201);
        assertTrue(zipCodesResponse.getZipCodes().containsAll(expectedZipCodes));
        assertThat(zipCodesResponse.getZipCodes()).doesNotHaveDuplicates();
    }

}
