package com.example.demo.controllers;

import com.example.demo.models.ClientDataPostReview;
import com.example.demo.models.CustomUserDetails;
import com.example.demo.models.Favorite;
import com.example.demo.models.Review;
import com.example.demo.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

/**
 * ReviewController
 */
@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

//    @GetMapping("/user/{userId}")
//    public List<Review> getUserReviews(@PathVariable int userId) {
//        return reviewService.getReviewsByUser(userId);
//    }

    /**
     * @return list of user reviews
     */
    @GetMapping("/user")
    public List<Review> getUserReviews() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return reviewService.getReviewsByUser(user.getId());
    }

    /**
     * @param data data containing information on what to add to review
     * @param movieId id of movie
     */
    // create a review
    // Not sure if 2 @Requestbody will work. Uncomment out the next createReview method if you think it would work.
    @PostMapping(value = "/create/{movieId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createReview(@RequestBody ClientDataPostReview data, @PathVariable String movieId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        reviewService.createReview(new Review(user.getId(), movieId, Integer.parseInt(data.getRating()), data.getReview()));
    }

    /**
     * @param review data contining information on review
     * @param movieId movie it
     */
    //     Creates a Review with a default rating of 1
    @PostMapping("/create/review/{movieId}")
    public void createReviewWithDefaultRating(@RequestBody String review, @PathVariable String movieId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        reviewService.createReview(new Review(user.getId(), movieId, 1, review));
    }

    /*
    @PutMapping("/editRating/{movieId}")
    public void editRating(@RequestBody int rating, @PathVariable String movieId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        reviewService.editRating(user.getId(), movieId, rating);
    }

    @PutMapping("/editReview/{movieId}")
    public void editReview(@RequestBody String review, @PathVariable String movieId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        reviewService.editReview(user.getId(), movieId, review);
    }
*/

    /**
     * @param movieId movie id
     */
    @DeleteMapping("/delete/{movieId}")
    public void deleteFromReviews(@PathVariable String movieId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        reviewService.deleteFromReviews(user.getId(), movieId);
    }

}