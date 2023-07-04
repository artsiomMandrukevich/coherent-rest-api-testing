import base.BaseTest;
import com.coherensolutions.rest.training.dto.response.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsersPostUploadTest extends BaseTest {

    @Order(1)
    @DisplayName("Scenario #1. Task 70. Upload users through /users/upload endpoint")
    @Test()
    void usersPostUploadGoldenPathTest() {
        User userFirst = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());
        User userSecond = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());
        List<User> userList = new ArrayList<>(List.of(userFirst, userSecond));
        String expectedResponseMessage = "Number of users = " + userList.size();

        // create a test zipCodes into the application
        List<String> expectedZipCodes = new ArrayList<>(List.of(userFirst.getZipCode(), userSecond.getZipCode()));
        clientZipCodes.sendPostZipCodes(expectedZipCodes, 201);

        // assert that responseCode is 201 and Response contains number of uploaded users
        assertEquals(expectedResponseMessage, clientUsers.sendPostUploadUsers(userFirst, userSecond, 201));
        // assert that All users are replaced with users from file
        assertThat(clientUsers.sendGetUsers()).containsAll(userList);
        assertThat(clientUsers.sendGetUsers().size()).isEqualTo(userList.size());
    }

    @Order(2)
    @DisplayName("Scenario #2. Task 70. Upload users with incorrect (unavailable) zip code")
    @Test()
    void usersPostUploadIncorrectZipCodeTest() {
        User userFirst = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());
        User userIncorrectZipCode = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setIncorrectZipCode());
        List<User> userList = new ArrayList<>(List.of(userFirst, userIncorrectZipCode));

        // create a test zipCodes into the application
        List<String> expectedZipCodes = new ArrayList<>(List.of(userFirst.getZipCode()));
        clientZipCodes.sendPostZipCodes(expectedZipCodes, 201);

        // assert that responseCode is 424
        clientUsers.sendPostUploadUsers(userFirst, userIncorrectZipCode, 424);
        // assert that Users are not uploaded
        assertThat(clientUsers.sendGetUsers()).doesNotContainAnyElementsOf(userList);
    }

    @Order(3)
    @DisplayName("Scenario #3. Task 70. Upload users and one of user has missed required fields.")
    @Test()
    void usersPostUploadMissedrequiredFieldsCodeTest() {
        User userFirst = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());
        User userMissedRequiredFields = new User(populator.setAge(), populator.setZipCode());
        List<User> userList = new ArrayList<>(List.of(userFirst, userMissedRequiredFields));

        // create a test zipCodes into the application
        List<String> expectedZipCodes = new ArrayList<>(List.of(userFirst.getZipCode(), userMissedRequiredFields.getZipCode()));
        clientZipCodes.sendPostZipCodes(expectedZipCodes, 201);

        // assert that responseCode is 409
        clientUsers.sendPostUploadUsers(userFirst, userMissedRequiredFields, 409);
        // assert that Users are not uploaded
        assertThat(clientUsers.sendGetUsers()).doesNotContainAnyElementsOf(userList);
    }

}
