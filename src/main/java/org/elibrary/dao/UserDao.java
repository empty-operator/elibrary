package org.elibrary.dao;

import org.elibrary.entity.Role;
import org.elibrary.entity.User;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements Dao<User> {

    private static UserDao instance;
    private static final String INSERT_USER = "INSERT INTO \"user\" (first_name, last_name, email, password, role_id) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE \"user\" SET first_name=(?), last_name=(?), email=(?), password=(?), banned=(?), role_id=(?) WHERE id=(?)";
    private static final String GET_USERS = "SELECT * FROM \"user\"";
    private static final String GET_USER_BY_ID = "SELECT first_name, last_name, email, password, banned, role_id FROM \"user\" WHERE id=(?)";
    private static final String GET_USER_BY_EMAIL = "SELECT id, first_name, last_name, password, banned, role_id FROM \"user\" WHERE email=(?)";

    private UserDao() {
    }

    public static synchronized UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    @Override
    public User get(int id) throws SQLException, NamingException {
        User user = new User();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    user.setId(id);
                    user.setFirstName(set.getString(1));
                    user.setLastName(set.getString(2));
                    user.setEmail(set.getString(3));
                    user.setPassword(set.getString(4));
                    user.setBanned(set.getBoolean(5));
                    user.setRole(Role.valueOf(set.getString(6).toUpperCase()));
                }
            }
        }
        return user;
    }

    public User getByEmail(String email) throws SQLException, NamingException {
        User user = null;
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_EMAIL)) {
            statement.setString(1, email);
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    user = new User();
                    user.setId(set.getInt(1));
                    user.setFirstName(set.getString(2));
                    user.setLastName(set.getString(3));
                    user.setEmail(email);
                    user.setPassword(set.getString(4));
                    user.setBanned(set.getBoolean(5));
                    user.setRole(Role.valueOf(set.getString(6).toUpperCase()));
                }
            }
        }
        return user;
    }

    @Override
    public List<User> getAll() throws SQLException, NamingException {
        List<User> users = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet set = statement.executeQuery(GET_USERS)) {
            while (set.next()) {
                User user = new User();
                user.setId(set.getInt(1));
                user.setFirstName(set.getString(2));
                user.setLastName(set.getString(3));
                user.setEmail(set.getString(4));
                user.setPassword(set.getString(5));
                user.setBanned(set.getBoolean(6));
                user.setRole(Role.valueOf(set.getString(7).toUpperCase()));
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public void insert(User user) throws SQLException, NamingException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, Role.READER.getValue());
            if (statement.executeUpdate() > 0) {
                try (ResultSet set = statement.getGeneratedKeys()) {
                    if (set.next()) {
                        user.setId(set.getInt(1));
                    }
                }
            }
        }
    }

    @Override
    public void update(User user) throws SQLException, NamingException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setBoolean(5, user.isBanned());
            statement.setString(6, user.getRole().getValue().toLowerCase());
            statement.setInt(7, user.getId());
            statement.executeUpdate();
        }
    }

}
