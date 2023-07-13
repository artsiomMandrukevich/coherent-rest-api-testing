package restassured;

import base.BaseTest;
import com.coherensolutions.rest.training.dto.response.User;
import io.qameta.allure.Description;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RaUsersPutPathUploadTest extends BaseTest {

    @Order(1)
    @DisplayName("Scenario #1. Task 50.")
    @Description("Send PUT/PATCH with full body.")
    @Test()
    void raUsersPutPatchGoldenPathTest() {
        User userToChangePut = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());
        User userToUpdatePut = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());
        User userToChangePatch = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());
        User userToUpdatePatch = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());

        // create a test zipCodes into the application
        List<String> expectedZipCodes = new ArrayList<>(List.of(userToChangePut.getZipCode(), userToUpdatePut.getZipCode(), userToChangePatch.getZipCode(), userToUpdatePatch.getZipCode()));
        raClientZipCodes.raSendPostZipCodes(expectedZipCodes, 201);

        // PUT
        // create a new user that will be changed through PUT endpoint.
        raClientUsers.raSendPostUsers(userToChangePut, 201);
        // update the user through PUT endpoint
        raClientUsers.raSendPutUsers(userToChangePut, userToUpdatePut, 200);
        // assert that there is no the changed user into application
        assertFalse(raClientUsers.raSendGetUsers(200).contains(userToChangePut));
        // assert that the updated user is added to application
        assertTrue(raClientUsers.raSendGetUsers(200).contains(userToUpdatePut));

        // PATCH
        // create a new user that will be changed through PATCH endpoint.
        raClientUsers.raSendPostUsers(userToChangePatch, 201);
        // update the user through PATCH endpoint
        raClientUsers.raSendPatchUsers(userToChangePatch, userToUpdatePatch, 200);
        // assert that there is no  the changed user into application
        assertFalse(raClientUsers.raSendGetUsers(200).contains(userToChangePatch));
        // assert that the updated user is added to application
        assertTrue(raClientUsers.raSendGetUsers(200).contains(userToUpdatePatch));
    }

    @Order(2)
    @DisplayName("Scenario #2. Task 50.")
    @Description("Send PUT/PATCH with incorrect zipCode.")
    @Test()
    void raUsersPutPatchIncorrectZipCodeTest() {
        User userToChangePut = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());
        User userToUpdatePut = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setIncorrectZipCode());
        User userToChangePatch = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());
        User userToUpdatePatch = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setIncorrectZipCode());

        // create a test zipCodes into the application
        List<String> expectedZipCodes = new ArrayList<>(List.of(userToChangePut.getZipCode(), userToChangePatch.getZipCode()));
        raClientZipCodes.raSendPostZipCodes(expectedZipCodes, 201);

        // PUT
        // create a new user that will be changed through PUT endpoint.
        raClientUsers.raSendPostUsers(userToChangePut, 201);
        // update the user through PUT endpoint
        raClientUsers.raSendPutUsers(userToChangePut, userToUpdatePut, 424);
        // assert that the changed user is not removed from the application
        assertTrue(raClientUsers.raSendGetUsers(200).contains(userToChangePut));
        // assert that the updated user is not added to application
        assertFalse(raClientUsers.raSendGetUsers(200).contains(userToUpdatePut));

        // PATCH
        // create a new user that will be changed through PATCH endpoint.
        raClientUsers.raSendPostUsers(userToChangePatch, 201);
        // update the user through PUT endpoint
        raClientUsers.raSendPatchUsers(userToChangePatch, userToUpdatePatch, 424);
        // assert that the changed user is not removed from the application
        assertTrue(raClientUsers.raSendGetUsers(200).contains(userToChangePatch));
        // assert that the updated user is not added to application
        assertFalse(raClientUsers.raSendGetUsers(200).contains(userToUpdatePatch));
    }

    @Order(3)
    @DisplayName("Scenario #3. Task 50.")
    @Description("Required fields are missing in PUT/PATCH.")
    @Test()
    void raUsersPutPatchRequiredFieldAreMissingTest() {
        User userToChangePut = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());
        User userToUpdatePut = new User(populator.setAge(), populator.setZipCode());
        User userToChangePatch = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());
        User userToUpdatePatch = new User(populator.setAge(), populator.setZipCode());

        // create a test zipCodes into the application
        List<String> expectedZipCodes = new ArrayList<>(List.of(userToChangePut.getZipCode(), userToChangePatch.getZipCode(), userToUpdatePatch.getZipCode()));
        raClientZipCodes.raSendPostZipCodes(expectedZipCodes, 201);

        // PUT
        // create a new user that will be changed through PUT endpoint.
        raClientUsers.raSendPostUsers(userToChangePut, 201);
        // update the user through PUT endpoint
        raClientUsers.raSendPutUsers(userToChangePut, userToUpdatePut, 409);
        // assert that the changed user is not removed from the application
        assertTrue(raClientUsers.raSendGetUsers(200).contains(userToChangePut));
        // assert that the updated user is not added to application
        assertFalse(raClientUsers.raSendGetUsers(200).contains(userToUpdatePut));

        // PATCH
        // create a new user that will be changed through PATCH endpoint.
        raClientUsers.raSendPostUsers(userToChangePatch, 201);
        // update the user through PATCH endpoint
        raClientUsers.raSendPatchUsers(userToChangePut, userToUpdatePut, 409);
        // assert that the changed user is not removed from the application
        assertTrue(raClientUsers.raSendGetUsers(200).contains(userToChangePatch));
        // assert that the updated user is not added to application
        assertFalse(raClientUsers.raSendGetUsers(200).contains(userToUpdatePatch));
    }

}
