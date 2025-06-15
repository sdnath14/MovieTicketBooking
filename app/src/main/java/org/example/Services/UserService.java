package org.example.Services;

import org.example.Utils.DatabaseConnection;

import java.sql.*;

public class UserService {
    public boolean registerUser(String username, String password) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);

            return ps.executeUpdate() > 0;
        }
    }

    public int loginUser(String username, String password) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT user_id FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("user_id");
            }
            return -1;
        }
    }
}
