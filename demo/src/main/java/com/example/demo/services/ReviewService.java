package com.example.demo.services;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.models.Review;
import com.example.demo.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * ReviewService
 */
@Service
@Transactional
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    public List<Review> getReviewsByUser(int userId) {
        return reviewRepository.findByUserId(userId);
    }

    /**
     * @param userId user id
     * @param movieId movie id
     */
    public void deleteFromReviews(int userId, String movieId) {
        reviewRepository.deleteByUserIdAndMovieId(userId, movieId);
    }

    /**
     * @param review review to add to db
     */
    public void createReview(Review review) {
        int userId = review.getUserId();
        String movieId = review.getMovieId();
        Review duplicate = reviewRepository.findByUserIdAndMovieId(userId, movieId);
        if (duplicate == null) {
            reviewRepository.save(review);
        } else {
            throw new DuplicateEntityException("You have already reviewed this movie");
        }
    }

//    public void editRating(int userId, String movieId, int rating) {
//        reviewRepository.updateRating(userId, movieId, rating);
//    }
//
//    public void editReview(int userId, String movieId, String review) {
//        reviewRepository.updateReview(userId, movieId, review);
//    }
}