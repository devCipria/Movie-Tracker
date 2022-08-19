package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class MovieList {
    @JsonProperty("Search")
    private List<Movie> movies;

    public MovieList() {
        this.movies = new ArrayList<>();
    }

    public List<Movie> getList() {
        return movies;
    }

    public void setList(List<Movie> list) {
        this.movies = list;
    }

}
