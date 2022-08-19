package com.example.demo.models;
import javax.persistence.*;

@Entity
@Table(name="watchlists")
public class Watchlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int userId;

    private String movieId;
    private boolean isPublic = true;

    public Watchlist() {
    }

    public Watchlist(String name, int userId, String movieId, boolean isPublic) {
        this.name = name;
        this.userId = userId;
        this.movieId = movieId;
        this.isPublic = isPublic;
    }

    public Watchlist(int id, String name, int userID, String movieId, boolean isPublic) {
        this.id = id;
        this.name = name;
        this.userId = userID;
        this.movieId = movieId;
        this.isPublic = isPublic;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userID) {
        this.userId = userID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}