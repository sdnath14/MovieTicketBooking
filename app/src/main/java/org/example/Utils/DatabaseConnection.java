package org.example.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // ✅ Use your actual database name instead of 'moviedb'
    private static final String URL = "jdbc:mysql://localhost:3306/ticketbooking?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";

    // ⚡ Make sure there is no trailing space in the password
    private static final String PASSWORD = "Java#Dev2024";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
