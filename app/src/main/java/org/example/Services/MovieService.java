package org.example.Services;

import org.example.Entities.Movie;
import org.example.Utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieService {

    public List<Movie> getAllMovies() throws SQLException {
        List<Movie> movies = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM movies";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                movies.add(new Movie(
                        rs.getInt("movie_id"),
                        rs.getString("movie_name"),
                        rs.getTime("show_time").toLocalTime(),
                        rs.getInt("total_seats")
                ));
            }
        }
        return movies;
    }

    // ✅ Add this method to fix the "cannot find symbol" error
    public Movie getMovieById(int movieId) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM movies WHERE movie_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, movieId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Movie(
                        rs.getInt("movie_id"),
                        rs.getString("movie_name"),
                        rs.getTime("show_time").toLocalTime(),
                        rs.getInt("total_seats")
                );
            } else {
                throw new SQLException("Movie not found with ID: " + movieId);
            }
        }
    }

}
