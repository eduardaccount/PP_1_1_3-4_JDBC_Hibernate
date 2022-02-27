package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection;

    public UserDaoJDBCImpl() {
        connection = Util.getConnection();
        if (connection == null) {
            try {
                throw new SQLException("Connection is null!");
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS users_table (" +
                    "user_id INT(10) PRIMARY KEY AUTO_INCREMENT, " +
                    "user_name VARCHAR(50), " +
                    "user_lastname VARCHAR(50), " +
                    "user_age INT)");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS users_table");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO users_table (user_name, user_lastname, user_age) " +
                    "VALUES ('" + name + "', '" + lastName + "', " + age + ");");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM users_table WHERE user_id = " + id);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> results = new ArrayList<>();
        ResultSet resultSet;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM users_table");
            while (resultSet.next()) {
                String name = resultSet.getString("user_name");
                String lastName = resultSet.getString("user_lastname");
                Byte age = (byte) resultSet.getInt("user_age");
                results.add(new User(name, lastName, age));
            }
            resultSet.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return results;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE users_table");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

}
