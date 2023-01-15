package org.mddg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnection {

    private static Connection singletonConnection;

    public static Connection getConnection() {
        if (MySqlConnection.singletonConnection != null) {
            return MySqlConnection.singletonConnection;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            MySqlConnection.singletonConnection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/sakila", "root", "example"
            );

            return MySqlConnection.singletonConnection;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
