package com.example.demo.services;

import com.example.demo.models.Movie;
import com.example.demo.models.MovieList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * OMDBService
 *
 * Handles accessing 3rd party APIs
 *
 * @author Tyler Kukkola
 */
@Service
public class OMDBService {
    private RestTemplate restTemplate;

    @Value("${OMDB_api_key}")
    private String api_key;

    public OMDBService() {}

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * @param movieTitle title of movie to search for
     * @return returns a list of matching movies
     */
    public List<Movie> getMoviesByTitle(String movieTitle) {
        String url = "https://www.omdbapi.com/?apikey=" + api_key +
                "&s=" + movieTitle +
                "&type=movie";
        MovieList resp = restTemplate.getForObject(url, MovieList.class);

        return resp != null? resp.getList() : new ArrayList<>();
    }

    /**
     * @param movieTitle movie title to search for
     * @param page page to get
     * @return returns list of matfhing movies
     */
    public List<Movie> getMoviesByTitle(String movieTitle, int page) {
        if (page < 1 || page > 100) // Invalid page
            return new ArrayList<>();

        String url = "https://www.omdbapi.com/?apikey=" + api_key +
                "&s=" + movieTitle +
                "&type=movie" +
                "&page=" + page;
        MovieList resp = restTemplate.getForObject(url, MovieList.class);

        return resp != null? resp.getList() : new ArrayList<>();
    }

    /**
     * @param movieId movie id
     * @return returns movie object
     */
    public Movie getMovieById(String movieId) {
        String url = "https://www.omdbapi.com/?apikey="+api_key+"&i=" + movieId;

        return restTemplate.getForObject(url, Movie.class);
    }
}