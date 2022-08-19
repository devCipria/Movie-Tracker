package com.example.demo.controllers;

import com.example.demo.models.CustomUserDetails;
import com.example.demo.models.Favorite;
import com.example.demo.services.FavoriteService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  Favorites controller
 */
@RestController
@RequestMapping("/favorite")
public class FavoriteController {

    @Autowired
    FavoriteService favoriteService;

    @Autowired
    private UserService userService;

    /**
     * getUserFavorites
     *
     * @param userId ID of user
     * @return returns list of users favorites
     */
    @GetMapping("/user/{userId}")
    public List<Favorite> getUserFavorites(@PathVariable int userId) {
        return favoriteService.getFavoritesByUser(userId);
    }

    /**
     * favoriteView
     *
     * @return test string
     */
    @GetMapping()
    public String favoriteView() {
        return "favorite";
    }

    /**
     * getUserFavorites
     *
     * @return Returns list of users favorited movies
     */
    @GetMapping("/user")
    public List<Favorite> getUserFavorites() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return favoriteService.getFavoritesByUser(user.getId());
    }

    /**
     * addToFvorites
     *
     * Adds a movie to a users favorites list from the movies id
     *
     * @param userId user id to use
     * @param movieId movie id to use
     */
    // add a movie to favorites
    @PostMapping("/user/{userId}/movie/{movieId}")
    public void addToFavorites(@PathVariable int userId, @PathVariable String movieId) {
        favoriteService.addToFavorites(new Favorite(userId, movieId));
    }

    /**
     * addToFavorites
     *
     * Adds a movie to a users favorites list from the movies id
     *
     * @param movieId movie id of the move to add to favorites
     */
    @PostMapping("/movie/{movieId}")
    public void addToFavorites(@PathVariable String movieId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        favoriteService.addToFavorites(new Favorite(user.getId(), movieId));
    }

    /**
     * deleteFromFavorites
     *
     * Deletes a movie from the users favorited movie list
     *
     * @param movieId movie id to delete from favorites
     */
    // Delete a movie from favorites
    @DeleteMapping("/delete/{movieId}")
    public void deleteFromFavorites(@PathVariable String movieId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        favoriteService.deleteFromFavorites(user.getId(), movieId);
    }

}