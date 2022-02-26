package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Statement statement;

    public UserDaoJDBCImpl() {
        try {
            statement = Util.getStatement();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void createUsersTable() {
        try {
            statement.execute("CREATE TABLE IF NOT EXISTS users_table (" +
                    "user_id INT(10) PRIMARY KEY AUTO_INCREMENT, " +
                    "user_name VARCHAR(50), " +
                    "user_lastname VARCHAR(50), " +
                    "user_age INT)");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            statement.execute("DROP TABLE IF EXISTS users_table");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO users_table (user_name, user_lastname, user_age) " +
                "VALUES ('" + name + "', '" + lastName + "', " + age + ");";
        try {
            statement.executeUpdate(query);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM users_table WHERE user_id = " + id;
        try {
            statement.execute(query);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List <User> results = new ArrayList<>();
        ResultSet resultSet;
        try {
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
        try {
            statement.execute("TRUNCATE TABLE users_table");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void closeConnections() {
        try {
            statement.close();
            Util.closeConnection();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
