package apache;

import base.BaseTest;
import com.coherensolutions.rest.training.dto.response.ZipCodesResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ZipCodesTest extends BaseTest {

    @Order(1)
    @DisplayName("Scenario #1. Task 20.")
    @Description("Get all available zip codes in the application for now.")
    @Test()
    void zipCodesGetTest() {
        List<String> expectedZipCodes = new ArrayList<>(Arrays.asList("12345" ,"23456", "ABCDE"));
        ZipCodesResponse zipCodesResponse = clientZipCodes.sendGetZipCodes(200);
        assertTrue(zipCodesResponse.getZipCodes().containsAll(expectedZipCodes));
    }

    @Order(2)
    @DisplayName("Scenario #2. Task 20.")
    @Description("Zip codes from request body are added to available zip codes of application.")
    @Test()
    void zipCodesPostExpandTest() {
        List<String> expectedZipCodes = new ArrayList<>(Arrays.asList("22201" ,"22202"));
        ZipCodesResponse zipCodesResponse = clientZipCodes.sendPostZipCodes(expectedZipCodes, 201);
        assertTrue(zipCodesResponse.getZipCodes().containsAll(expectedZipCodes));
    }

    @Order(3)
    @DisplayName("Scenario #3. Task 20.")
    @Description("Zip codes from request body are added to available zip codes of application. There are no duplications in available zip codes")
    @Issue("Duplicate zipCodes are created in app")
    @Test()
    void zipCodesPostDuplicationAvailableTest() {
        List<String> expectedZipCodes = new ArrayList<>(Arrays.asList("44401", "44402", "12345"));
        ZipCodesResponse zipCodesResponse = clientZipCodes.sendPostZipCodes(expectedZipCodes, 201);
        assertTrue(zipCodesResponse.getZipCodes().containsAll(expectedZipCodes));
        assertThat(zipCodesResponse.getZipCodes()).doesNotHaveDuplicates();
    }

    @Order(4)
    @DisplayName("Scenario #4. Task 20.")
    @Description("Zip codes from request body are added to available zip codes of application. There are no duplications between available zip codes and already used zip code")
    @Issue("Duplicate zipCodes are created in app")
    @Test()
    void zipCodesPostDuplicationUsedTest() {
        List<String> expectedZipCodes = new ArrayList<>(Arrays.asList("33301", "33302", "33302"));
        ZipCodesResponse zipCodesResponse = clientZipCodes.sendPostZipCodes(expectedZipCodes, 201);
        assertTrue(zipCodesResponse.getZipCodes().containsAll(expectedZipCodes));
        assertThat(zipCodesResponse.getZipCodes()).doesNotHaveDuplicates();
    }

}
