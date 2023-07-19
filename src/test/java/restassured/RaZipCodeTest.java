package restassured;

import base.BaseTest;
import io.qameta.allure.Description;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RaZipCodeTest extends BaseTest {

    @Order(1)
    @DisplayName("Scenario #1. Task 20.")
    @Description("Get all available zip codes in the application for now.")
    @Test
    public void raZipCodesGetTest() {
        List<String> expectedZipCodes = new ArrayList<>(Arrays.asList("12345" ,"23456", "ABCDE"));
        // get available zipCodes from application
        List<String> actualZipCodes = raClientZipCodes.raSendGetZipCodes(200);
        // assert
        assertTrue(actualZipCodes.containsAll(expectedZipCodes));
    }

    @Order(2)
    @DisplayName("Scenario #2. Task 20.")
    @Description("Zip codes from request body are added to available zip codes of application.")
    @Test
    public void raZipCodesPostExpandTest() {
        List<String> expectedZipCodes = new ArrayList<>(Arrays.asList("22201" ,"22202"));
        // add new zipCodes into application
        raClientZipCodes.raSendPostZipCodes(expectedZipCodes, 201);
        // get available zipCodes from application
        List<String> actualZipCodes = raClientZipCodes.raSendGetZipCodes(200);
        // assert
        assertTrue(actualZipCodes.containsAll(expectedZipCodes));
    }

    @Order(3)
    @DisplayName("Scenario #3. Task 20.")
    @Description("Zip codes from request body are added to available zip codes of application. There are no duplications in available zip codes")
    @Test
    public void raZipCodesPostDuplicationAvailableTest() {
        List<String> expectedZipCodes = new ArrayList<>(Arrays.asList("44401", "44402", "12345"));
        // add new zipCodes into application
        raClientZipCodes.raSendPostZipCodes(expectedZipCodes, 201);
        // get available zipCodes from application
        List<String> actualZipCodes = raClientZipCodes.raSendGetZipCodes(200);
        // assert
        assertTrue(actualZipCodes.containsAll(expectedZipCodes));
        assertThat(actualZipCodes).doesNotHaveDuplicates();
    }

    @Order(4)
    @DisplayName("Scenario #4. Task 20.")
    @Description("Zip codes from request body are added to available zip codes of application. There are no duplications between available zip codes and already used zip code")
    @Test()
    public void raZipCodesPostDuplicationUsedTest() {
        List<String> expectedZipCodes = new ArrayList<>(Arrays.asList("33301", "33302", "33302"));
        // add new zipCodes into application
        raClientZipCodes.raSendPostZipCodes(expectedZipCodes, 201);
        // get available zipCodes from application
        List<String> actualZipCodes = raClientZipCodes.raSendGetZipCodes(200);
        // assert
        assertTrue(actualZipCodes.containsAll(expectedZipCodes));
        assertThat(actualZipCodes).doesNotHaveDuplicates();
    }

}
