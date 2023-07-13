package base;

import com.coherensolutions.rest.training.helpers.PropertiesHelper;
import com.coherensolutions.rest.training.http.ClientUsers;
import com.coherensolutions.rest.training.http.ClientZipCodes;
import com.coherensolutions.rest.training.restassured.RaClientUsers;
import com.coherensolutions.rest.training.restassured.RaClientZipCodes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import utils.RandomPopulator;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    protected ClientZipCodes clientZipCodes;
    protected ClientUsers clientUsers;
    protected RandomPopulator populator;
    protected PropertiesHelper props;

    protected RaClientZipCodes raClientZipCodes;
    protected RaClientUsers raClientUsers;

    @BeforeAll
    public void classInitializationApache() {
        clientZipCodes = new ClientZipCodes();
        clientUsers = new ClientUsers();
        populator = new RandomPopulator();
        props = new PropertiesHelper();
    }

    @BeforeAll
    public void classInitializationRestAssured() {
        raClientZipCodes = new RaClientZipCodes();
        raClientUsers = new RaClientUsers();
    }

}
