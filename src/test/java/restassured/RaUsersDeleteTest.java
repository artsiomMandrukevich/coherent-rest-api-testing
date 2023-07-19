package restassured;

import base.BaseTest;
import com.coherensolutions.rest.training.dto.response.User;
import io.qameta.allure.Description;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RaUsersDeleteTest extends BaseTest {

    @Order(1)
    @DisplayName("Scenario #1. Task 60.")
    @Description("Send DELETE with full body.")
    @Test()
    void raUsersDeleteUserGoldenPathTest() {
        User userToDelete = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());
        // create a test zipCodes into the application
        List<String> expectedZipCodes = new ArrayList<>(List.of(userToDelete.getZipCode()));
        raClientZipCodes.raSendPostZipCodes(expectedZipCodes, 201);
        // create a new user that will be changed through PUT endpoint.
        raClientUsers.raSendPostUsers(userToDelete, 201);

        // delete the user through delete endpoint
        raClientUsers.raSendDeleteUsers(userToDelete, 204);
        // assert that the user has been deleted from the application
        assertFalse(raClientUsers.raSendGetUsers(200).contains(userToDelete));
        // assert that the Zip code is returned in list of available zip codes
        assertTrue(raClientZipCodes.raSendGetZipCodes(200).contains(userToDelete.getZipCode()));
    }

    @Order(2)
    @DisplayName("Scenario #2. Task 60.")
    @Description("Send DELETE with only required fields.")
    @Test()
    void raUsersDeleteUserRequiredFieldsPresentedTest() {
        User userToDelete = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());
        User userToDeleteJson = new User(userToDelete.getName(), userToDelete.getSex());
        // create a test zipCodes into the application
        List<String> expectedZipCodes = new ArrayList<>(List.of(userToDelete.getZipCode()));
        raClientZipCodes.raSendPostZipCodes(expectedZipCodes, 201);
        // create a new user that will be changed through PUT endpoint.
        raClientUsers.raSendPostUsers(userToDelete, 201);

        // delete the user through delete endpoint
        raClientUsers.raSendDeleteUsers(userToDeleteJson, 204);
        // assert that the user has been deleted from the application
        assertFalse(raClientUsers.raSendGetUsers(200).contains(userToDelete));
        // assert that the Zip code is returned in list of available zip codes
        assertTrue(raClientZipCodes.raSendGetZipCodes(200).contains(userToDelete.getZipCode()));
    }

    @Order(3)
    @DisplayName("Scenario #3. Task 60.")
    @Description("Send DELETE without required fields.")
    @Test()
    void raUsersDeleteUserRequiredFieldsMissedTest() {
        User userToDelete = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());
        User userToDeleteJson = new User(userToDelete.getAge(), userToDelete.getZipCode());
        // create a test zipCodes into the application
        List<String> expectedZipCodes = new ArrayList<>(List.of(userToDelete.getZipCode()));
        raClientZipCodes.raSendPostZipCodes(expectedZipCodes, 201);
        // create a new user that will be changed through PUT endpoint.
        raClientUsers.raSendPostUsers(userToDelete, 201);

        // delete the user through delete endpoint
        raClientUsers.raSendDeleteUsers(userToDeleteJson, 409);
        // assert that the user has been deleted from the application
        assertTrue(raClientUsers.raSendGetUsers(200).contains(userToDelete));
    }

}
