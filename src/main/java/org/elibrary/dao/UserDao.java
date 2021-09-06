package org.elibrary.dao;

import org.elibrary.entity.Role;
import org.elibrary.entity.User;

import javax.naming.NamingException;
import java.sql.*;

public class UserDao {

    private static UserDao instance;
    private static final String INSERT_USER = "INSERT INTO \"user\" (first_name, last_name, email, password, role_id) VALUES (?, ?, ?, ?, ?)";
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
        User user = new User();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_EMAIL)) {
            statement.setString(1, email);
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
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

}
