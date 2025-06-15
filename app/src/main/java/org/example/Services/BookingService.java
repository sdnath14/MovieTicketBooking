package org.example.Services;

import org.example.Entities.Ticket;
import org.example.Exceptions.BookingException;
import org.example.Utils.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class BookingService {

    // ✅ Get Available Seats
    public List<Integer> getAvailableSeats(int movieId) throws SQLException {
        List<Integer> availableSeats = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String seatQuery = "SELECT seat_number FROM tickets WHERE movie_id = ?";
            PreparedStatement ps = conn.prepareStatement(seatQuery);
            ps.setInt(1, movieId);
            ResultSet rs = ps.executeQuery();

            Set<Integer> bookedSeats = new HashSet<>();
            while (rs.next()) {
                bookedSeats.add(rs.getInt("seat_number"));
            }

            String movieQuery = "SELECT total_seats FROM movies WHERE movie_id = ?";
            ps = conn.prepareStatement(movieQuery);
            ps.setInt(1, movieId);
            rs = ps.executeQuery();

            if (rs.next()) {
                int totalSeats = rs.getInt("total_seats");
                for (int i = 1; i <= totalSeats; i++) {
                    if (!bookedSeats.contains(i)) {
                        availableSeats.add(i);
                    }
                }
            }
        }
        return availableSeats;
    }

    // ✅ Book Ticket
    public void bookTicket(int userId, int movieId, int seatNumber) throws SQLException, BookingException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String checkSeat = "SELECT * FROM tickets WHERE movie_id = ? AND seat_number = ?";
            PreparedStatement ps = conn.prepareStatement(checkSeat);
            ps.setInt(1, movieId);
            ps.setInt(2, seatNumber);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                throw new BookingException("❌ Seat already booked! Please select another seat.");
            }

            String insertTicket = "INSERT INTO tickets (user_id, movie_id, seat_number) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(insertTicket);
            ps.setInt(1, userId);
            ps.setInt(2, movieId);
            ps.setInt(3, seatNumber);
            ps.executeUpdate();

            System.out.println("\n✅ Seat booked successfully!\n");
        }
    }

    // ✅ Get User's Booked Tickets (Detailed)
    public List<Ticket> getUserTickets(int userId) throws SQLException {
        List<Ticket> tickets = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT t.ticket_id, t.movie_id, t.seat_number, m.movie_name, m.show_time " +
                    "FROM tickets t " +
                    "JOIN movies m ON t.movie_id = m.movie_id " +
                    "WHERE t.user_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tickets.add(new Ticket(
                        rs.getInt("ticket_id"),
                        rs.getInt("movie_id"),
                        rs.getString("movie_name"),
                        rs.getTime("show_time").toLocalTime(),
                        rs.getInt("seat_number")
                ));
            }
        }

        return tickets;
    }
}
