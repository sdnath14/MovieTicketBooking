package org.example;

import org.example.Entities.Movie;
import org.example.Entities.Ticket;
import org.example.Exceptions.BookingException;
import org.example.Services.BookingService;
import org.example.Services.MovieService;
import org.example.Services.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();
        MovieService movieService = new MovieService();
        BookingService bookingService = new BookingService();

        try {
            while (true) {
                System.out.println("===================================");
                System.out.println("\uD83C\uDFAC Welcome to Movie Ticket Booking \uD83C\uDFAC");
                System.out.println("===================================");
                System.out.println("1. Login\n2. Sign Up\n3. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 1) {
                    System.out.println("\n------ Login ------");
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    int userId = userService.loginUser(username, password);
                    if (userId != -1) {
                        System.out.println("\n✅ Login successful! Welcome, " + username + "!\n");
                        showUserMenu(userId, movieService, bookingService, username);
                    } else {
                        System.out.println("\n❌ Invalid credentials!\n");
                    }

                } else if (choice == 2) {
                    System.out.println("\n------ Sign Up ------");
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    boolean registered = userService.registerUser(username, password);
                    if (registered) {
                        System.out.println("\n✅ User registered successfully!\n");
                    } else {
                        System.out.println("\n❌ Registration failed!\n");
                    }

                } else if (choice == 3) {
                    System.out.println("\n✅ Thank you for using the Ticket Booking System!\n");
                    break;
                } else {
                    System.out.println("\n❌ Invalid choice!\n");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void showUserMenu(int userId, MovieService movieService, BookingService bookingService, String username) throws SQLException, BookingException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. View Movies\n2. Book Ticket\n3. View My Tickets\n4. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                List<Movie> movies = movieService.getAllMovies();
                System.out.println("===================================");
                System.out.println("🎥 Available Movies");
                System.out.println("===================================");

                for (Movie movie : movies) {
                    int availableSeats = bookingService.getAvailableSeats(movie.getMovieId()).size();
                    System.out.println("Movie ID: " + movie.getMovieId() + " | " + movie.getMovieName() + " | Show Time: " + movie.getShowTime() + " | Seats Available: " + availableSeats);
                }
            } else if (choice == 2) {
                System.out.print("Enter Movie ID to Book: ");
                int movieId = scanner.nextInt();

                List<Integer> availableSeats = bookingService.getAvailableSeats(movieId);
                if (availableSeats.isEmpty()) {
                    System.out.println("\n❌ No seats available!\n");
                    continue;
                }

                System.out.println("\nAvailable Seats: " + availableSeats);
                System.out.print("Enter Seat Number: ");
                int seatNumber = scanner.nextInt();

                try {
                    bookingService.bookTicket(userId, movieId, seatNumber);
                    Movie bookedMovie = movieService.getMovieById(movieId);
                    System.out.println("\n✅ Seat booked successfully!\n");
                    System.out.println("\uD83C\uDF9F️ Your Ticket:");
                    System.out.println("-------------------------------");
                    System.out.println("Movie: " + bookedMovie.getMovieName());
                    System.out.println("Show Time: " + bookedMovie.getShowTime());
                    System.out.println("Seat Number: " + seatNumber);
                    System.out.println("-------------------------------\n");

                } catch (BookingException e) {
                    System.out.println("\n❌ " + e.getMessage() + "\n");
                }

            } else if (choice == 3) {
                List<Ticket> tickets = bookingService.getUserTickets(userId);
                if (tickets.isEmpty()) {
                    System.out.println("\n❌ You have no booked tickets.\n");
                    continue;
                }

                System.out.println("\n\uD83C\uDF9F️ Your Booked Tickets:");
                System.out.println("-------------------------------");
                tickets.forEach(ticket -> {
                    try {
                        Movie movie = movieService.getMovieById(ticket.getMovieId());
                        System.out.println("Movie: " + movie.getMovieName() + " | Show Time: " + movie.getShowTime() + " | Seat: " + ticket.getSeatNumber());
                    } catch (SQLException e) {
                        System.out.println("Error fetching movie: " + e.getMessage());
                    }
                });
                System.out.println("-------------------------------\n");

            } else if (choice == 4) {
                System.out.println("\n✅ You have been logged out. Thank you for using the Ticket Booking System!\n");
                break;
            } else {
                System.out.println("\n❌ Invalid choice!\n");
            }
        }
    }
}
