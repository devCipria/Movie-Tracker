package com.example.demo.controllers;

import com.example.demo.models.Movie;
import com.example.demo.services.OMDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * OMDBController
 *
 * Handles searching for and getting information to clients
 *
 * @author Tyler Kukkola
 */
@RestController
@RequestMapping("/omdb")
public class OMDBController {
    @Autowired
    private OMDBService omdbService;

    /**
     * searchForMovieTitle
     *
     * @param movieTitle movie name to search for
     * @return returns list of movies
     */
    @GetMapping("/searchName/{movieTitle}")
    public List<Movie> searchForMovieTitle(@PathVariable String movieTitle) {
        return omdbService.getMoviesByTitle(movieTitle);
    }

    /**
     * search for movie id
     *
     * returns a single movie object
     *
     * @param movieId movie id to search for
     * @return movie object
     */
    @GetMapping("/searchId/{movieId}")
    public Movie searchForMovieId(@PathVariable String movieId) {
        return omdbService.getMovieById(movieId);
    }
}