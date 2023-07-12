import base.BaseTest;
import com.coherensolutions.rest.training.dto.response.User;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsersPostTest extends BaseTest {

    @Order(1)
    @DisplayName("Scenario #1. Task 30.")
    @Description("User is added to application when all fields are filled in")
    @Test()
    void usersPostAllFieldsTest() {
        User expectedUser = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());
        List<String> expectedZipCodes = new ArrayList<>(Collections.singletonList(expectedUser.getZipCode()));
        // create a test zipCode into the application
        clientZipCodes.sendPostZipCodes(expectedZipCodes, 201);
        // create a new user with zipCode from previous steps. assert statusCode = 201.
        assertEquals(201, clientUsers.sendPostUsers(expectedUser));
        // assert that User is added to application
        assertTrue(clientUsers.sendGetUsers().contains(expectedUser));
        // assert that Zip code is removed from available zip codes of application
        assertThat(clientZipCodes.sendGetZipCodes(201).getZipCodes()).doesNotContain(expectedZipCodes.get(0));
    }

    @Order(2)
    @DisplayName("Scenario #2. Task 30.")
    @Description("User is added to application when REQUIRED fields are filled in.")
    @Test()
    void usersPostRequiredFieldsTest() {
        User expectedUser = new User(populator.setName(), populator.setAge(), populator.setSex());
        // create a new user without zipCode value. assert statusCode = 201.
        assertEquals(201, clientUsers.sendPostUsers(expectedUser));
        // assert that User is added to application
        assertTrue(clientUsers.sendGetUsers().contains(expectedUser));
    }

    @Order(3)
    @DisplayName("Scenario #3. Task 30.")
    @Description("User is not added to application when the zipCode is incorrect.")
    @Test()
    void usersPostIncorrectZipCodeTest() {
        User expectedUser = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setIncorrectZipCode());
        // create a new user with incorrect zipCode. assert statusCode = 424.
        assertEquals(424, clientUsers.sendPostUsers(expectedUser));
        // assert that User is not added to application
        assertFalse(clientUsers.sendGetUsers().contains(expectedUser));
    }

    @Order(4)
    @DisplayName("Scenario #4. Task 30.")
    @Description("User is not added with the same name and sex as existing user in the system.")
    @Issue("StatusCode of POST endpoint is 400 instead of 201")
    @Test()
    void usersPostDuplicateUserTest() {
        String duplicatedName = populator.setName();
        String duplicatedSex = populator.setSex();
        User newUser = new User(duplicatedName, populator.setAge(), duplicatedSex, populator.setZipCode());
        List<String> newUserZipCodes = new ArrayList<>(Collections.singletonList(newUser.getZipCode()));
        User duplicateUser = new User(duplicatedName, populator.setAge(), duplicatedSex, populator.setZipCode());
        List<String> duplicateUserZipCodes = new ArrayList<>(Collections.singletonList(duplicateUser.getZipCode()));
        // create two test zipCodes into the application
        clientZipCodes.sendPostZipCodes(newUserZipCodes, 201);
        clientZipCodes.sendPostZipCodes(duplicateUserZipCodes, 201);
        // create a new user in the application
        clientUsers.sendPostUsers(newUser);
        // create a duplicate user in the application (the same name + sex). assert statusCode = 400
        assertEquals(400, clientUsers.sendPostUsers(duplicateUser));
        // assert that User is not added to application
        assertFalse(clientUsers.sendGetUsers().contains(newUser));
    }

}
