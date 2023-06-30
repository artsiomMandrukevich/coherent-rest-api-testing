package base;

import com.coherensolutions.rest.training.http.ClientUsers;
import com.coherensolutions.rest.training.http.ClientZipCodes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import utils.RandomPopulator;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    protected ClientZipCodes clientZipCodes;
    protected ClientUsers clientUsers;
    protected RandomPopulator populator;

    @BeforeAll
    public void classInitialization() {
        clientZipCodes = new ClientZipCodes();
        clientUsers = new ClientUsers();
        populator = new RandomPopulator();
    }

}
