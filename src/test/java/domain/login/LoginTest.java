package domain.login;

import edu.csu.web_last.domain.service.login.DefaultLogin;
import edu.csu.web_last.domain.service.login.ILogin;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {

    ILogin login = new DefaultLogin();
    Logger logger = Logger.getLogger(DefaultLogin.class.getName());

    @Test
    public void testLogin() {
        assertTrue(login.login("admin", "admin"));
        assertFalse(login.login("admin", "test"));

    }

    @Test
    public void testRemember() {
        String jwt = login.remember(true, "nikos");
        logger.info(jwt);
    }

    @Test
    public void testCheckSuccess() {
        String jwt = login.remember(true, "nikos");
        logger.info(jwt);
        assertTrue(login.loginCheck(jwt));
    }

//    @Test
//    public void testCheckFail() {
//        String jwt = login.remember(true, "nikos");
//        logger.info(jwt);
//        assertFalse(login.loginCheck(jwt));
//    }
}
