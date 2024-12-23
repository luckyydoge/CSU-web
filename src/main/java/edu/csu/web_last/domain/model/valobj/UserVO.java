package edu.csu.web_last.domain.model.valobj;

import lombok.Data;

@Data
public class UserVO {
    private String username;
    private String password;
    private Boolean rememberme;
}
