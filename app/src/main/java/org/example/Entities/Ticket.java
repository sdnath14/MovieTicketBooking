package org.example.Entities;

import java.time.LocalTime;

public class Ticket {
    private int ticketId;
    private int movieId;
    private String movieName;
    private LocalTime showTime;
    private int seatNumber;

    public Ticket(int ticketId, int movieId, String movieName, LocalTime showTime, int seatNumber) {
        this.ticketId = ticketId;
        this.movieId = movieId;
        this.movieName = movieName;
        this.showTime = showTime;
        this.seatNumber = seatNumber;
    }

    public int getTicketId() {
        return ticketId;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public LocalTime getShowTime() {
        return showTime;
    }

    public int getSeatNumber() {
        return seatNumber;
    }
}
