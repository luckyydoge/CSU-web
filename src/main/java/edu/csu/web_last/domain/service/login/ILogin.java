package edu.csu.web_last.domain.service.login;

public interface ILogin {
    Boolean login(String username, String password);
    Boolean loginCheck(String jwt);
    String remember(Boolean remember, String username);
}
