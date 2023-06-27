package base;

import com.coherensolutions.rest.training.http.ClientUsers;
import com.coherensolutions.rest.training.http.ClientZipCodes;
import org.junit.jupiter.api.BeforeEach;
import utils.RandomPopulator;

public class BaseTest {

    protected ClientZipCodes clientZipCodes;
    protected ClientUsers clientUsers;
    protected RandomPopulator populator;

    @BeforeEach
    public void classInitialization() {
        clientZipCodes = new ClientZipCodes();
        clientUsers = new ClientUsers();
        populator = new RandomPopulator();
    }

}
