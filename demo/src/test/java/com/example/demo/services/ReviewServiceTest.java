//package com.example.demo.services;
//
//import com.example.demo.DemoApplication;
//import com.example.demo.exceptions.DuplicateEntityException;
//import com.example.demo.models.Review;
//import com.example.demo.repositories.ReviewRepository;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(classes = DemoApplication.class)
//@TestPropertySource("classpath:application.properties")
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class ReviewServiceTest {
//    @Autowired
//    private ReviewService reviewService;
//
//    @Autowired
//    private ReviewRepository reviewRepository;
//
//    @Test
//    @Order(1)
//    public void verify_getReviewsByUser() {
//        int userId = 3;
//
//        // Verify that userId 3 does not have any reviews
//        List<Review> reviews = reviewService.getReviewsByUser(userId);
//        assertTrue(reviews.isEmpty());
//
//        // Add a record to the reviews table for userId 3
//        reviewService.createReview(new Review(userId, "fakeMovieId", 3, "Great"));
//
//        // Retrieve the list of reviews from user 3
//        reviews = reviewService.getReviewsByUser(userId);
//        assertNotNull(reviews);
//        assertEquals(1, reviews.size());
//        assertEquals(3, reviews.get(0).getUserId());
//        assertEquals("fakeMovieId", reviews.get(0).getMovieId());
//
//        // Delete the reviews from userId 3
//        reviewService.deleteFromReviews(userId, "fakeMovieId");
//    }
//
//    @Test
//    @Order(2)
//    public void verify_createReview() {
//        // Verifies that the reviews table does not include a record with a userId=1 and movieId='fakeMovieId'
//        Review review = reviewRepository.findByUserIdAndMovieId(1, "fakeMovieId");
//        assertNull(review);
//
//        // Add a record to the reviews table with a userId=1 and movieId='fakeMovieId'
//        review = new Review(1, "fakeMovieId", 3, "Great");
//        reviewService.createReview(review);
//
//        // Verifies that reviewService.createReview() has added the record to the reviews table
//        Review retrievedReview = reviewRepository.findByUserIdAndMovieId(1, "fakeMovieId");
//        assertEquals(review.getId(), retrievedReview.getId());
//        assertEquals(review.getMovieId(), retrievedReview.getMovieId());
//    }
//
//    @Test
//    @Order(3)
//    public void verify_deleteFromReviews() {
//        // Verifies that there exists a record in the reviews table with a userId=1 and movieId='fakeMovieId'
//        Review review = reviewRepository.findByUserIdAndMovieId(1, "fakeMovieId");
//        assertEquals(1, review.getUserId());
//        assertEquals("fakeMovieId", review.getMovieId());
//
//        // Deletes the record in the reviews table with a userId=1 and movieId='fakeMovieId'
//        reviewService.deleteFromReviews(1, "fakeMovieId");
//
//        // Verifies that the reviews table does not include a record with a userId=1 and movieId='fakeMovieId'
//        Review retrievedReview = reviewRepository.findByUserIdAndMovieId(1, "fakeMovieId");
//        assertNull(retrievedReview);
//    }
//
//    @Test
//    @Order(4)
//    public void verify_createReviewThrowsException() {
//        try {
//            // Add a record to the reviews table with a userId=3 and movieId='fakeMovieId'
//            Review review = new Review( 3, "fakeMovieId", 1, "This is a review");
//            reviewService.createReview(review);
//
//            // Add the same movie review to userId 3's reviews
//            Throwable exception = assertThrows(DuplicateEntityException.class, () -> reviewService.createReview(review));
//            assertEquals("You have already reviewed this movie", exception.getMessage());
//        } finally{
//            // clean up - delete review from reviews table
//            reviewService.deleteFromReviews(3, "fakeMovieId");
//        }
//    }
//}
