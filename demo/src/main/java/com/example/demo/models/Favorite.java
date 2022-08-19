package com.example.demo.models;
import javax.persistence.*;



@Entity
@Table(name="favorites")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    private String movieId;

    public Favorite() {
    }

    public Favorite(int userId, String movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }

    public Favorite(int id, int userId, String movieId) {
        this.id = id;
        this.userId = userId;
        this.movieId = movieId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}