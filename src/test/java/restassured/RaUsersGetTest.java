package restassured;

import base.BaseTest;
import com.coherensolutions.rest.training.dto.response.User;
import com.coherensolutions.rest.training.http.GetKeyParameter;
import io.qameta.allure.Description;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RaUsersGetTest extends BaseTest {

    @BeforeAll
    public void prepareTestUsersData() {
        for (int i = 35; i < 40; i++) {
            User expectedUser = new User(populator.setName(), i, populator.setSex(), populator.setZipCode());
            List<String> expectedZipCodes = new ArrayList<>(Collections.singletonList(expectedUser.getZipCode()));
            raClientZipCodes.raSendPostZipCodes(expectedZipCodes, 201);
            raClientUsers.raSendPostUsers(expectedUser, 201);
        }
    }

    @Order(1)
    @DisplayName("Scenario #2. Task 40.")
    @Description("Validate that the olderThan filter works as expected.")
    @Test()
    void raUsersGetOlderThanTest() {
        int valueParam = 37;
        List<User> users = raClientUsers.raSendGetUsers(200, GetKeyParameter.OLDERTHAN, String.valueOf(valueParam));
        assertThat(users).filteredOnAssertions(user -> assertThat(user.getAge()).isLessThan(valueParam)).hasSize(0);
    }

    @Order(2)
    @DisplayName("Scenario #3. Task 40.")
    @Description("Validate the youngerThan filter works as expected")
    @Test()
    void raUsersGetYoungerThanTest() {
        int valueParam = 37;
        List<User> users = raClientUsers.raSendGetUsers(200, GetKeyParameter.YOUNGERTHAN, String.valueOf(valueParam));
        assertThat(users).filteredOnAssertions(user -> assertThat(user.getAge()).isGreaterThan(valueParam)).hasSize(0);
    }

    @Order(3)
    @DisplayName("Scenario #4. Task 40.")
    @Description("Validate the sex filter works as expected.")
    @Test()
    void raUsersGetSexTest() {
        String valueParam = "FEMALE";
        List<User> users = raClientUsers.raSendGetUsers(200, GetKeyParameter.SEX, valueParam);
        assertThat(users).filteredOnAssertions(user -> assertThat(user.getSex()).isNotEqualTo(valueParam)).hasSize(0);
    }

}
