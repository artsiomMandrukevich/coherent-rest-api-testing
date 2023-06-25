import base.BaseTest;
import com.coherensolutions.rest.training.dto.response.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsersPostTest extends BaseTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Order(1)
    @DisplayName("Scenario #1. Task 30. User is added to application when all fields are filled in")
    @Test()
    void usersPostAllFieldsTest() {
        final String expectedUserJson = "{\"age\":17,\"name\":\"John\", \"sex\" : \"MALE\", \"zipCode\" : \"44410\"}";
        User expectedUser =  objectMapper.readValue(expectedUserJson, User.class);
        List<String> expectedZipCodes = new ArrayList<>(Collections.singletonList(expectedUser.getZipCode()));
        // create a test zipCode into the application
        clientZipCodes.sendPostZipCodes(expectedZipCodes, 201);
        // create a new user with zipCode from previous steps. assert statusCode = 201.
        assertEquals(201, clientUsers.sendPostUsers(expectedUserJson));
        // assert that User is added to application
        assertTrue(clientUsers.sendGetUsers().contains(expectedUser));
        // assert that Zip code is removed from available zip codes of application
        assertThat(clientZipCodes.sendGetZipCodes(201).getZipCodes()).doesNotContain(expectedZipCodes.get(0));
    }

    @SneakyThrows
    @Order(2)
    @DisplayName("Scenario #2. Task 30. User is added to application when all fields are filled in")
    @Test()
    void usersPostRequiredFieldsTest() {
        final String expectedUserJson = "{\"age\":27,\"name\":\"Becky\", \"sex\" : \"FEMALE\"}";
        User expectedUser =  objectMapper.readValue(expectedUserJson, User.class);
        // create a new user without zipCode value. assert statusCode = 201.
        assertEquals(201, clientUsers.sendPostUsers(expectedUserJson));
        // assert that User is added to application
        assertTrue(clientUsers.sendGetUsers().contains(expectedUser));
    }

    @SneakyThrows
    @Order(3)
    @DisplayName("Scenario #3. Task 30. User is not added to application when the zipCode is incorrect")
    @Test()
    void usersPostIncorrectZipCodeTest() {
        final String expectedUserJson = "{\"age\":17,\"name\":\"John\", \"sex\" : \"MALE\", \"zipCode\" : \"999999\"}";
        User expectedUser =  objectMapper.readValue(expectedUserJson, User.class);
        // create a new user with incorrect zipCode. assert statusCode = 424.
        assertEquals(424, clientUsers.sendPostUsers(expectedUserJson));
        // assert that User is not added to application
        assertFalse(clientUsers.sendGetUsers().contains(expectedUser));
    }

    @SneakyThrows
    @Order(4)
    @DisplayName("Scenario #4. Task 30. User is not added with the same name and sex as existing user in the system")
    @Test()
    void usersPostDuplicateUserTest() {
        final String newUserJson = "{\"age\":83,\"name\":\"Duplicate\", \"sex\" : \"MALE\", \"zipCode\" : \"87654\"}";
        User newUser =  objectMapper.readValue(newUserJson, User.class);
        List<String> newUserZipCodes = new ArrayList<>(Collections.singletonList(newUser.getZipCode()));
        final String duplicateUserJson = "{\"age\":23,\"name\":\"Duplicate\", \"sex\" : \"MALE\", \"zipCode\" : \"45678\"}";
        User duplicateUser =  objectMapper.readValue(duplicateUserJson, User.class);
        List<String> duplicateUserZipCodes = new ArrayList<>(Collections.singletonList(duplicateUser.getZipCode()));
        // create two test zipCodes into the application
        clientZipCodes.sendPostZipCodes(newUserZipCodes, 201);
        clientZipCodes.sendPostZipCodes(duplicateUserZipCodes, 201);
        // create a new user in the application
        clientUsers.sendPostUsers(newUserJson);
        // create a duplicate user in the application (the same name + sex). assert statusCode = 400
        assertEquals(400, clientUsers.sendPostUsers(duplicateUserJson));
        // assert that User is not added to application
        assertFalse(clientUsers.sendGetUsers().contains(newUser));
    }

}