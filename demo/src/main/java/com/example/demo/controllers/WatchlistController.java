package com.example.demo.controllers;

import com.example.demo.models.CustomUserDetails;

import com.example.demo.models.Watchlist;
import com.example.demo.services.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * WatchlistController
 */
@RestController
@RequestMapping("/watchlist")
public class WatchlistController {
    @Autowired
    private WatchlistService watchlistService;

    /**
     * @return list of watchlist items
     */
    @GetMapping("/user")
    public List<Watchlist> getUserWatchlist() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return watchlistService.getWatchlistByUserId(user.getId());
    }

//    @GetMapping("/name/{name}")
//    public List<Watchlist> getWatchListByName(@PathVariable String name) {
//        return watchlistService.getWatchlistByName(name);
//    }

    /**
     * @param name name of watchlist to get
     * @return list of watchlist objects
     */
    @GetMapping("user/name/{name}")
    public List<Watchlist> getWatchListByNameAndID(@PathVariable String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return watchlistService.getWatchlistByUserIdAndName(user.getId(),name);
    }

    /**
     * @param movieId movie id
     */
    @DeleteMapping("/user/delete/movie/{movieId}")
    public void deleteWatchListByUserIdAndMovieId(@PathVariable String movieId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        watchlistService.deleteWatchlistByUserIdAndMovieId(user.getId(), movieId);
    }

    /**
     * @param movieId movie id
     */
    @PostMapping("user/create/movie/{movieId}")
    public void addToWatchlist(@PathVariable String movieId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        watchlistService.addToWatchlist(new Watchlist("watchlist",user.getId(), movieId,true));
    }
}