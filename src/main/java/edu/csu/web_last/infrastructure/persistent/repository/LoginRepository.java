package edu.csu.web_last.infrastructure.persistent.repository;

import edu.csu.web_last.domain.model.entity.User;
import edu.csu.web_last.domain.repository.IRepository;

import java.sql.*;

public class LoginRepository implements IRepository {
    String url = "jdbc:mysql://localhost:3306/web_last";
    String dbUsername = "root";
    String dbpassword = "123456";
    @Override
    public User queryUserByUsername(String username) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 显式加载驱动
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String sql = "select * from user where username=?";
        User user = new User();
        try {
            Connection connection = DriverManager.getConnection(url, dbUsername, dbpassword);
            System.out.println("Connected to MySQL database!");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String userName = resultSet.getString("username");
                String password = resultSet.getString("password");
                user.setUsername(username);
                user.setPassword(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }
}
