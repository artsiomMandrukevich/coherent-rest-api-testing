package restassured;

import base.BaseTest;
import com.coherensolutions.rest.training.dto.response.User;
import io.qameta.allure.Description;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RaUsersPostTest extends BaseTest {

    @Order(1)
    @DisplayName("Scenario #1. Task 30.")
    @Description("User is added to application when all fields are filled in")
    @Test()
    void raUsersPostAllFieldsTest() {
        User expectedUser = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());
        List<String> expectedZipCodes = new ArrayList<>(Collections.singletonList(expectedUser.getZipCode()));
        // create a test zipCode into the application
        raClientZipCodes.raSendPostZipCodes(expectedZipCodes, 201);
        // create a new user with zipCode from previous steps. assert statusCode = 201.
        raClientUsers.raSendPostUsers(expectedUser, 201);
        // assert that User is added to application
        assertTrue(raClientUsers.raSendGetUsers(200).contains(expectedUser));
        // assert that Zip code is removed from available zip codes of application
        assertThat(raClientZipCodes.raSendGetZipCodes(200)).doesNotContain(expectedZipCodes.get(0));
    }

    @Order(2)
    @DisplayName("Scenario #2. Task 30.")
    @Description("User is added to application when REQUIRED fields are filled in.")
    @Test()
    void raUsersPostRequiredFieldsTest() {
        User expectedUser = new User(populator.setName(), populator.setAge(), populator.setSex());
        // create a new user without zipCode value. assert statusCode = 201.
        raClientUsers.raSendPostUsers(expectedUser, 201);
        // assert that User is added to application
        assertTrue(raClientUsers.raSendGetUsers(200).contains(expectedUser));
    }

    @Order(3)
    @DisplayName("Scenario #3. Task 30.")
    @Description("User is not added to application when the zipCode is incorrect.")
    @Test()
    void raUsersPostIncorrectZipCodeTest() {
        User expectedUser = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setIncorrectZipCode());
        // create a new user with incorrect zipCode. assert statusCode = 424.
        raClientUsers.raSendPostUsers(expectedUser, 424);
        // assert that User is not added to application
        assertFalse(raClientUsers.raSendGetUsers(200).contains(expectedUser));
    }

    @Order(4)
    @DisplayName("Scenario #4. Task 30.")
    @Description("User is not added with the same name and sex as existing user in the system.")
    @Test()
    void raUsersPostDuplicateUserTest() {
        String duplicatedName = populator.setName();
        String duplicatedSex = populator.setSex();
        User newUser = new User(duplicatedName, populator.setAge(), duplicatedSex, populator.setZipCode());
        User duplicateUser = new User(duplicatedName, populator.setAge(), duplicatedSex, populator.setZipCode());
        List<String> zipCodes = new ArrayList<>(List.of(newUser.getZipCode(), duplicateUser.getZipCode()));

        // create two test zipCodes into the application
        raClientZipCodes.raSendPostZipCodes(zipCodes, 201);
        // create a new user in the application
        raClientUsers.raSendPostUsers(newUser, 201);
        // create a duplicate user in the application (the same name + sex). assert statusCode = 400
        raClientUsers.raSendPostUsers(duplicateUser, 400);
        // assert that User is not added to application
        assertFalse(raClientUsers.raSendGetUsers(200).contains(duplicateUser));
    }

}
