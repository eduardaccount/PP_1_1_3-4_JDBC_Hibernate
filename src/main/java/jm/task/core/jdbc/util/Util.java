package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {

    private static final String url = "jdbc:mysql://localhost:3306/ppschema";
    private static final String user = "root";
    private static final String password = "12345678";

    public static Statement getStatement() throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection.createStatement();
    }
}
