package edu.csu.web_last.domain.service.login;

import edu.csu.web_last.domain.model.entity.User;
import edu.csu.web_last.domain.repository.IRepository;
import edu.csu.web_last.infrastructure.persistent.repository.LoginRepository;
import edu.csu.web_last.utils.IUtils;
import edu.csu.web_last.utils.Utils;

import java.util.logging.Logger;

public class DefaultLogin implements ILogin {
    IRepository repository;
    IUtils utils;
    LoginSupport loginSupport;
    Logger logger = Logger.getLogger(this.getClass().getName());

    public DefaultLogin() {
        this.repository = new LoginRepository();
        this.loginSupport = new LoginSupport();
        this.utils = new Utils();
    }

    @Override
    public Boolean login(String username, String password) {
        logger.info(username + " try to login");

        User user = repository.queryUserByUsername(username);
        logger.info(user.toString());
        if (null == user) {
            return false;
        }
        return loginSupport.verify(user, password);
    }

    @Override
    public Boolean loginCheck(String jwt) {
        return loginSupport.loginCheck(jwt);
    }

    @Override
    public String remember(Boolean remember, String username) {
        String jwt = null;
        if (!remember) {
            return loginSupport.remember(username, 3 * 1000);
        }
        jwt =  loginSupport.remember(username);
        logger.info(username + " remember jwt " + jwt);
        return jwt;
    }
}
