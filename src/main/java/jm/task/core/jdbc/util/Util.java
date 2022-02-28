package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {

    private static final String url = "jdbc:mysql://localhost:3306/ppschema";
    private static final String user = "root";
    private static final String password = "12345678";
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
