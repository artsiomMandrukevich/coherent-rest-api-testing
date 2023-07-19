package restassured;

import base.BaseTest;
import com.coherensolutions.rest.training.dto.response.User;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RaUsersPostUploadTest extends BaseTest {

    @Order(1)
    @DisplayName("Scenario #1. Task 70.")
    @Description("Upload users through /users/upload endpoint.")
    @Test()
    void raUsersPostUploadGoldenPathTest() {
        User userFirst = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());
        User userSecond = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());
        List<User> userList = new ArrayList<>(List.of(userFirst, userSecond));
        String expectedResponseMessage = "Number of users = " + userList.size();

        // create a test zipCodes into the application
        List<String> expectedZipCodes = new ArrayList<>(List.of(userFirst.getZipCode(), userSecond.getZipCode()));
        raClientZipCodes.raSendPostZipCodes(expectedZipCodes, 201);

        // assert that responseCode is 201 and Response contains number of uploaded users
        assertEquals(expectedResponseMessage, raClientUsers.raSendPostUploadUsers(userFirst, userSecond, 201));
        // assert that All users are replaced with users from file
        assertThat(raClientUsers.raSendGetUsers(200)).containsAll(userList);
        assertThat(raClientUsers.raSendGetUsers(200).size()).isEqualTo(userList.size());
    }

    @Order(2)
    @DisplayName("Scenario #2. Task 70.")
    @Description("Upload users with incorrect (unavailable) zip code.")
    @Issue("StatusCode of POST /upload endpoint is 500 instead of 424")
    @Test()
    void raUsersPostUploadIncorrectZipCodeTest() {
        User userFirst = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());
        User userIncorrectZipCode = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setIncorrectZipCode());
        List<User> userList = new ArrayList<>(List.of(userFirst, userIncorrectZipCode));

        // create a test zipCodes into the application
        List<String> expectedZipCodes = new ArrayList<>(List.of(userFirst.getZipCode()));
        raClientZipCodes.raSendPostZipCodes(expectedZipCodes, 201);

        // assert that responseCode is 424
        raClientUsers.raSendPostUploadUsers(userFirst, userIncorrectZipCode, 424);
        // assert that Users are not uploaded
        assertThat(raClientUsers.raSendGetUsers(200)).doesNotContainAnyElementsOf(userList);
    }

    @Order(3)
    @DisplayName("Scenario #3. Task 70.")
    @Description("Upload users and one of user has missed required fields.")
    @Issue("StatusCode of POST /upload endpoint is 500 instead of 409")
    @Test()
    void raUsersPostUploadMissedrequiredFieldsCodeTest() {
        User userFirst = new User(populator.setName(), populator.setAge(), populator.setSex(), populator.setZipCode());
        User userMissedRequiredFields = new User(populator.setAge(), populator.setZipCode());
        List<User> userList = new ArrayList<>(List.of(userFirst, userMissedRequiredFields));

        // create a test zipCodes into the application
        List<String> expectedZipCodes = new ArrayList<>(List.of(userFirst.getZipCode(), userMissedRequiredFields.getZipCode()));
        raClientZipCodes.raSendPostZipCodes(expectedZipCodes, 201);

        // assert that responseCode is 409
        raClientUsers.raSendPostUploadUsers(userFirst, userMissedRequiredFields, 409);
        // assert that Users are not uploaded
        assertThat(raClientUsers.raSendGetUsers(200)).doesNotContainAnyElementsOf(userList);
    }

}
