package edu.csu.web_last.domain.repository;

import edu.csu.web_last.domain.model.entity.User;

public interface IRepository {
    User queryUserByUsername(String username);
}
