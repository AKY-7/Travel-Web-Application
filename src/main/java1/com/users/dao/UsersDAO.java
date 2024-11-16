package com.users.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.users.model.Users;

public class UsersDAO {
    private String jdbcURL = "jdbc:mysql://localhost:30006/Travel_Web";
    private String jdbcUserName = "root";
    private String jdbcPassword = "12345";

    // SQL Queries
    private static final String INSERT_USERS_SQL = "INSERT INTO Users (fullName, email, password, role, phoneNumber, address, registrationDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM Users WHERE userId = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM Users";
    private static final String DELETE_USER_SQL = "DELETE FROM Users WHERE userId = ?";
    private static final String UPDATE_USER_SQL = "UPDATE Users SET fullName = ?, email = ?, password = ?, role = ?, phoneNumber = ?, address = ? WHERE userId = ?";

    // JDBC Connection
    protected Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcURL, jdbcUserName, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Insert User
    public void insertUser(Users user) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.setString(5, user.getPhoneNumber());
            preparedStatement.setString(6, user.getAddress());
            preparedStatement.setString(7, user.getRegistrationDate());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Select User by ID
    public Users selectUser(int userId) {
        Users user = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String fullName = rs.getString("fullName");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String role = rs.getString("role");
                String phoneNumber = rs.getString("phoneNumber");
                String address = rs.getString("address");
                String registrationDate = rs.getString("registrationDate");
                user = new Users(userId, fullName, email, password, role, phoneNumber, address, registrationDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Select All Users
    public List<Users> selectAllUsers() {
        List<Users> users = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("userId");
                String fullName = rs.getString("fullName");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String role = rs.getString("role");
                String phoneNumber = rs.getString("phoneNumber");
                String address = rs.getString("address");
                String registrationDate = rs.getString("registrationDate");
                users.add(new Users(userId, fullName, email, password, role, phoneNumber, address, registrationDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Delete User
    public boolean deleteUser(int userId) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL)) {
            preparedStatement.setInt(1, userId);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    // Update User
    public boolean updateUser(Users user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL)) {
            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.setString(5, user.getPhoneNumber());
            preparedStatement.setString(6, user.getAddress());
            preparedStatement.setInt(7, user.getUserId());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
}