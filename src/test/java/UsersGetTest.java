import base.BaseTest;
import com.coherensolutions.rest.training.dto.response.User;
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
    @DisplayName("Scenario #2. Task 40. Validate that the olderThan filter.")
    @Test()
    void usersGetOlderThanTest() {
        String keyParam = "olderThan";
        int valueParam = 37;
        List<User> users = clientUsers.sendGetUsers(keyParam, String.valueOf(valueParam));
        assertThat(users).filteredOnAssertions(user -> assertThat(user.getAge()).isLessThan(valueParam)).hasSize(0);
    }

    @Order(2)
    @DisplayName("Scenario #3. Task 40. Validate the youngerThan filter")
    @Test()
    void usersGetYoungerThanTest() {
        String keyParam = "youngerThan";
        int valueParam = 37;
        List<User> users = clientUsers.sendGetUsers(keyParam, String.valueOf(valueParam));
        assertThat(users).filteredOnAssertions(user -> assertThat(user.getAge()).isGreaterThan(valueParam)).hasSize(0);
    }

    @Order(2)
    @DisplayName("Scenario #4. Task 40. Validate the sex filter.")
    @Test()
    void usersGetSexTest() {
        String keyParam = "sex";
        String valueParam = "FEMALE";
        List<User> users = clientUsers.sendGetUsers(keyParam, valueParam);
        assertThat(users).filteredOnAssertions(user -> assertThat(user.getSex()).isNotEqualTo(valueParam)).hasSize(0);
    }

}
