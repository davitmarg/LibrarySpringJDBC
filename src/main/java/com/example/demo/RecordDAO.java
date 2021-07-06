package com.example.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecordDAO {
    static final String DB_URL = "jdbc:mariadb://localhost:3306/library";
    static final String USER = "root";
    static final String PASS = "";
    private String tableName = "record";

    public RecordDAO() {
        createTable();
    }

    public void createTable() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                    "    id INT NOT NULL AUTO_INCREMENT UNIQUE," +
                    "    user_id INT NOT NULL ," +
                    "    book_id INT NOT NULL UNIQUE," +
                    "    date DATETIME," +
                    "    PRIMARY KEY (id)," +
                    "    FOREIGN KEY (book_id) REFERENCES book(id)," +
                    "    FOREIGN KEY (user_id) REFERENCES user(id)" +
                    ")";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.execute();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<BookRecord> selectAll() {
        List<BookRecord> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM " + tableName;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int user_id = resultSet.getInt("user_id");
                    int book_id = resultSet.getInt("book_id");
                    Date date = resultSet.getDate("date");
                    list.add(new BookRecord(id, user_id, book_id, date));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public BookRecord selectRecordByID(int id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM " + tableName + " WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int user_id = resultSet.getInt("user_id");
                    int book_id = resultSet.getInt("book_id");
                    Date date = resultSet.getDate("date");
                    return new BookRecord(id, user_id, book_id, date);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
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

    public BookRecord insertRecord(BookRecord record) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "INSERT INTO " + tableName + "(user_id, book_id, date) " +
                    "VALUES(?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, record.getUser_id());
                preparedStatement.setInt(2, record.getBook_id());
                preparedStatement.setDate(3, record.getDate());
                preparedStatement.execute();
                return new BookRecord(lastID(),record.getUser_id(),record.getBook_id(),record.getDate());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public BookRecord updateRecord(int id, BookRecord record) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "UPDATE " + tableName + " " +
                    "SET user_id = ?, book_id = ?, date = ?" +
                    "WHERE id=?;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, record.getUser_id());
                preparedStatement.setInt(2, record.getBook_id());
                preparedStatement.setDate(3, record.getDate());
                preparedStatement.execute();

                return new BookRecord(id,record.getUser_id(),record.getBook_id(),record.getDate());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    public void deleteRecord(int id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "DELETE FROM " + tableName + " WHERE id = ? ";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                preparedStatement.execute();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
