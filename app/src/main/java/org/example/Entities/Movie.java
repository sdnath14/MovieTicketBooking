package org.example.Entities;

import java.time.LocalTime;

public class Movie {

    private int movieId;
    private String movieName;
    private LocalTime showTime;
    private int totalSeats;

    public Movie(int movieId, String movieName, LocalTime showTime, int totalSeats) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.showTime = showTime;
        this.totalSeats = totalSeats;
    }

    public int getMovieId() { return movieId; }
    public String getMovieName() { return movieName; }
    public LocalTime getShowTime() { return showTime; }
    public int getTotalSeats() { return totalSeats; }
}

