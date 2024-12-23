package infrastructure;

import edu.csu.web_last.domain.model.entity.User;
import edu.csu.web_last.domain.repository.IRepository;
import edu.csu.web_last.infrastructure.persistent.repository.LoginRepository;
import org.junit.jupiter.api.Test;

public class LoginTest {
    IRepository repository = new LoginRepository();
    @Test
    public void testQueryByUsername() {
        User user = repository.queryUserByUsername("admin");
        System.out.println(user.toString());
    }

    @Test
    public void testQueryByUsernameNotExist() {
        User user = repository.queryUserByUsername("1");
        System.out.println(user.toString());
    }
}
