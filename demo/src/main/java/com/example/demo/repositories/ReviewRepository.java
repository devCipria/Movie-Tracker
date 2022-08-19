package com.example.demo.repositories;

import com.example.demo.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByUserId(int userId);

    void deleteByUserIdAndMovieId(int userId, String movieId);

//    @Query(
//            nativeQuery = true,
//            value = "update reviews set rating=:rating where user_id=:userId and movie_id=:movieId"
//    )
//    void updateRating(@Param("userId") int userId, @Param("movieId") String movieId, @Param("rating") int rating);
//
//    @Query(
//            nativeQuery = true,
//            value = "update reviews set review=:review where user_id=:userId and movie_id=:movieId"
//    )
//    void updateReview(@Param("userId") int userId, @Param("movieId") String movieId, @Param("review") String review);

    Review findByUserIdAndMovieId(int userId, String movieId);
}