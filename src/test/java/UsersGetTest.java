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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsersGetTest extends BaseTest {

    @BeforeAll
    public void prepareTestUsersData() {
        for (int i = 35; i < 40; i++) {
            User expectedUser = new User(populator.setName(), i, populator.setSex(), populator.setZipCode());
            List<String> expectedZipCodes = new ArrayList<>(Collections.singletonList(expectedUser.getZipCode()));
            clientZipCodes.sendPostZipCodes(expectedZipCodes, 201);
            clientUsers.sendPostUsers(expectedUser);
        }
    }

    @Order(1)
    @DisplayName("Scenario #2. Task 40.")
    @Description("Validate that the olderThan filter works as expected.")
    @Test()
    void usersGetOlderThanTest() {
        int valueParam = 37;
        List<User> users = clientUsers.sendGetUsers(GetKeyParameter.OLDERTHAN, String.valueOf(valueParam));
        assertThat(users).filteredOnAssertions(user -> assertThat(user.getAge()).isLessThan(valueParam)).hasSize(0);
    }

    @Order(2)
    @DisplayName("Scenario #3. Task 40.")
    @Description("Validate the youngerThan filter works as expected")
    @Test()
    void usersGetYoungerThanTest() {
        int valueParam = 37;
        List<User> users = clientUsers.sendGetUsers(GetKeyParameter.YOUNGERTHAN, String.valueOf(valueParam));
        assertThat(users).filteredOnAssertions(user -> assertThat(user.getAge()).isGreaterThan(valueParam)).hasSize(0);
    }

    @Order(3)
    @DisplayName("Scenario #4. Task 40.")
    @Description("Validate the sex filter works as expected.")
    @Test()
    void usersGetSexTest() {
        String valueParam = "FEMALE";
        List<User> users = clientUsers.sendGetUsers(GetKeyParameter.SEX, valueParam);
        assertThat(users).filteredOnAssertions(user -> assertThat(user.getSex()).isNotEqualTo(valueParam)).hasSize(0);
    }

}
