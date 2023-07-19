package apache;

import base.BaseTest;
import com.coherensolutions.rest.training.dto.response.User;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsersDeleteTest extends BaseTest {

    @Order(1)
    @DisplayName("Scenario #1. Task 60.")
    @Description("Send DELETE with full body.")
    @Test()
    void usersDeleteUserGoldenPathTest() {
        User userToDelete = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());
        // create a test zipCodes into the application
        List<String> expectedZipCodes = new ArrayList<>(List.of(userToDelete.getZipCode()));
        clientZipCodes.sendPostZipCodes(expectedZipCodes, 201);
        // create a new user that will be changed through PUT endpoint.
        clientUsers.sendPostUsers(userToDelete);

        // delete the user through delete endpoint
        assertEquals(204, clientUsers.sendDeleteUsers(userToDelete));
        // assert that the user has been deleted from the application
        assertFalse(clientUsers.sendGetUsers().contains(userToDelete));
        // assert that the Zip code is returned in list of available zip codes
        assertTrue(clientZipCodes.sendGetZipCodes(200).getZipCodes().contains(userToDelete.getZipCode()));
    }

    @Order(2)
    @DisplayName("Scenario #2. Task 60.")
    @Description("Send DELETE with only required fields.")
    @Issue("User is not deleted from application")
    @Test()
    void usersDeleteUserRequiredFieldsPresentedTest() {
        User userToDelete = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());
        User userToDeleteJson = new User(userToDelete.getName(), userToDelete.getSex());
        // create a test zipCodes into the application
        List<String> expectedZipCodes = new ArrayList<>(List.of(userToDelete.getZipCode()));
        clientZipCodes.sendPostZipCodes(expectedZipCodes, 201);
        // create a new user that will be changed through PUT endpoint.
        clientUsers.sendPostUsers(userToDelete);

        // delete the user through delete endpoint
        assertEquals(204, clientUsers.sendDeleteUsers(userToDeleteJson));
        // assert that the user has been deleted from the application
        assertFalse(clientUsers.sendGetUsers().contains(userToDelete));
        // assert that the Zip code is returned in list of available zip codes
        assertTrue(clientZipCodes.sendGetZipCodes(200).getZipCodes().contains(userToDelete.getZipCode()));
    }

    @Order(3)
    @DisplayName("Scenario #3. Task 60.")
    @Description("Send DELETE without required fields.")
    @Test()
    void usersDeleteUserRequiredFieldsMissedTest() {
        User userToDelete = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());
        User userToDeleteJson = new User(userToDelete.getAge(), userToDelete.getZipCode());
        // create a test zipCodes into the application
        List<String> expectedZipCodes = new ArrayList<>(List.of(userToDelete.getZipCode()));
        clientZipCodes.sendPostZipCodes(expectedZipCodes, 201);
        // create a new user that will be changed through PUT endpoint.
        clientUsers.sendPostUsers(userToDelete);

        // delete the user through delete endpoint
        assertEquals(409, clientUsers.sendDeleteUsers(userToDeleteJson));
        // assert that the user has been deleted from the application
        assertTrue(clientUsers.sendGetUsers().contains(userToDelete));
    }

}
