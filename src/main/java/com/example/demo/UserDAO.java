package com.example.demo;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    static final String DB_URL = "jdbc:mariadb://localhost:3306/library";
    static final String USER = "root";
    static final String PASS = "";

    private String tableName = "user";

    public UserDAO() {
        createTable();
    }

    public void createTable() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = " CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    "id INT AUTO_INCREMENT UNIQUE, " +
                    "name VARCHAR(30)," +
                    "surname VARCHAR(30)," +
                    "mail VARCHAR(30)," +
                    "username VARCHAR(30)," +
                    "password VARCHAR(150)," +
                    "status ENUM('ADMIN','USER')," +
                    "PRIMARY KEY(id))";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.execute();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Integer lastID() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT id FROM " + tableName + " ORDER BY id DESC LIMIT 1";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    return resultSet.getInt("id");
                }
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public User insertUser(User user) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "INSERT INTO " + tableName + " (name, surname, mail, username, password, status) " +
                    "VALUES(?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                user.setPassword(getHash(user.getPassword()));
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getSurname());
                preparedStatement.setString(3, user.getMail());
                preparedStatement.setString(4, user.getUsername());
                preparedStatement.setString(5, user.getPassword());
                preparedStatement.setString(6, user.getStatus().name());
                preparedStatement.execute();
                return new User(lastID(), user.getName(), user.getSurname(), user.getMail(), user.getUsername(), user.getPassword(), user.getStatus());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public User selectUserByID(int id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM " + tableName +
                    " WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    String mail = resultSet.getString("mail");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    Status status = Status.valueOf(resultSet.getString("status"));
                    return new User(id, name, surname, mail, username, password, status);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    private boolean checkPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

    private String getHash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User findUser(String username, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM " + tableName +
                    " WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String hash = resultSet.getString("password");
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    String mail = resultSet.getString("mail");
                    Status status = Status.valueOf(resultSet.getString("status"));

                    if (checkPassword(password, hash)) {
                        return new User(id, name, surname, mail, username, hash, status);
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<User> selectAll() {
        List<User> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM " + tableName;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    String mail = resultSet.getString("mail");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    Status status = Status.valueOf(resultSet.getString("status"));
                    list.add(new User(id, name, surname, mail, username, password, status));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }
}

