package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String request = "CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastName VARCHAR(255), age INT);";
        try (Connection connection = Util.getConnection();
                PreparedStatement prpStmnt = connection.prepareStatement(request)) {
            prpStmnt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String request = "drop table Users";
        try (Connection connection = Util.getConnection();
                PreparedStatement prpStmnt = connection.prepareStatement(request)) {
            prpStmnt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String request = "insert into Users (name, lastName, age) values (?, ?, ?)";
        try (Connection connection = Util.getConnection();
                PreparedStatement prpStmnt = connection.prepareStatement(request)) {
            prpStmnt.setString(1, name);
            prpStmnt.setString(2, lastName);
            prpStmnt.setByte(3, age);
            prpStmnt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String request = "delete from Users where id = ?";
        try (Connection connection = Util.getConnection();
                PreparedStatement prpStmnt = connection.prepareStatement(request)) {
            prpStmnt.setLong(1, id);
            prpStmnt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String request = "select * from users";
        try (Connection connection = Util.getConnection();
                    PreparedStatement prpstmnt = connection.prepareStatement(request)) {
            ResultSet resultSet = prpstmnt.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(Long.valueOf(resultSet.getString("id")));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(Byte.valueOf(resultSet.getString("age")));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        String request = "delete from Users";
        try (Connection connection = Util.getConnection();
                PreparedStatement prpStmnt = connection.prepareStatement(request)) {
            prpStmnt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
