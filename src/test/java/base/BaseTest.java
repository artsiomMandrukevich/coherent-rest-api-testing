package base;

import com.coherensolutions.rest.training.http.ClientUsers;
import com.coherensolutions.rest.training.http.ClientZipCodes;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    protected ClientZipCodes clientZipCodes;
    protected ClientUsers clientUsers;

    @BeforeEach
    public void classInitialization() {
        clientZipCodes = new ClientZipCodes();
        clientUsers = new ClientUsers();
    }

}
