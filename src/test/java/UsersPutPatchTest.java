import base.BaseTest;
import com.coherensolutions.rest.training.dto.response.User;
import org.junit.jupiter.api.*;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsersPutPatchTest extends BaseTest {

    @BeforeAll
    public void prepareTestUsersData() {
    }

    @Order(1)
    @DisplayName("Scenario #1. Task 50. Send PUT/PATCH with full body.")
    @Test()
    void usersPutPathGoldenPathTest() {
        User userToChange = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());
        User userToUpdate = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());

        // create a test zipCodes into the application
        List<String> expectedZipCodes = new ArrayList<>(List.of(userToUpdate.getZipCode(), userToChange.getZipCode(), userToChange.getZipCode(), userToUpdate.getZipCode()));
        clientZipCodes.sendPostZipCodes(expectedZipCodes, 201);

        // PUT
        // create a new user that will be changed through PUT endpoint.
        clientUsers.sendPostUsers(userToChange);
        // update the user through PUT endpoint
        assertEquals(200, clientUsers.sendPutUsers(userToChange, userToUpdate));
        // assert that there is no the changed user into application
        assertFalse(clientUsers.sendGetUsers().contains(userToChange));
        // assert that the updated user is added to application
        assertTrue(clientUsers.sendGetUsers().contains(userToUpdate));

        // PATCH
        // create a new user that will be changed through PATCH endpoint.
        clientUsers.sendPostUsers(userToChange);
        // update the user through PATCH endpoint
        assertEquals(200, clientUsers.sendPatchUsers(userToChange, userToUpdate));
        // assert that there is no  the changed user into application
        assertFalse(clientUsers.sendGetUsers().contains(userToChange));
        // assert that the updated user is added to application
        assertTrue(clientUsers.sendGetUsers().contains(userToUpdate));

    }

    @Order(2)
    @DisplayName("Scenario #2. Task 50. Send PUT/PATCH with incorrect zipCode.")
    @Test()
    void usersPutPathIncorrectZipCodeTest() {
        User userToChangePut = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());
        User userToUpdatePut = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setIncorrectZipCode());
        User userToChangePatch = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());
        User userToUpdatePatch = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setIncorrectZipCode());

        // create a test zipCodes into the application
        List<String> expectedZipCodes = new ArrayList<>(List.of(userToChangePut.getZipCode(), userToChangePatch.getZipCode()));
        clientZipCodes.sendPostZipCodes(expectedZipCodes, 201);

        // PUT
        // create a new user that will be changed through PUT endpoint.
        clientUsers.sendPostUsers(userToChangePut);
        // update the user through PUT endpoint
        assertEquals(424, clientUsers.sendPutUsers(userToChangePut, userToUpdatePut));
        // assert that the changed user is not removed from the application
        assertTrue(clientUsers.sendGetUsers().contains(userToChangePut));
        // assert that the updated user is not added to application
        assertFalse(clientUsers.sendGetUsers().contains(userToUpdatePut));

        // PATCH
        // create a new user that will be changed through PATCH endpoint.
        clientUsers.sendPostUsers(userToChangePatch);
        // update the user through PATCH endpoint
        assertEquals(424, clientUsers.sendPatchUsers(userToChangePatch, userToUpdatePatch));
        // assert that the changed user is not removed from the application
        assertTrue(clientUsers.sendGetUsers().contains(userToChangePatch));
        // assert that the updated user is not added to application
        assertFalse(clientUsers.sendGetUsers().contains(userToUpdatePatch));

    }

    @Order(3)
    @DisplayName("Scenario #3. Task 50. Required fields are missing in PUT/PATCH.")
    @Test()
    void usersPutPathRequiredFieldAreMissingTest() {
        User userToChangePut = new User(populator.setAge(), populator.setZipCode());
        User userToUpdatePut = new User(populator.setAge(), populator.setZipCode());
        User userToChangePatch = new User(populator.setAge(), populator.setZipCode());
        User userToUpdatePatch = new User(populator.setAge(), populator.setZipCode());

        // create a test zipCodes into the application
        List<String> expectedZipCodes = new ArrayList<>(List.of(userToChangePut.getZipCode(), userToChangePatch.getZipCode()));
        clientZipCodes.sendPostZipCodes(expectedZipCodes, 201);

        // PUT
        // create a new user that will be changed through PUT endpoint.
        clientUsers.sendPostUsers(userToChangePut);
        // update the user through PUT endpoint
        assertEquals(409, clientUsers.sendPutUsers(userToChangePut, userToUpdatePut));
        // assert that the changed user is not removed from the application
        assertTrue(clientUsers.sendGetUsers().contains(userToChangePut));
        // assert that the updated user is not added to application
        assertFalse(clientUsers.sendGetUsers().contains(userToUpdatePut));

        // PATCH
        // create a new user that will be changed through PATCH endpoint.
        clientUsers.sendPostUsers(userToChangePatch);
        // update the user through PATCH endpoint
        assertEquals(400, clientUsers.sendPatchUsers(userToChangePatch, userToUpdatePatch));
        // assert that the changed user is not removed from the application
        assertTrue(clientUsers.sendGetUsers().contains(userToChangePatch));
        // assert that the updated user is not added to application
        assertFalse(clientUsers.sendGetUsers().contains(userToUpdatePatch));

    }
}
