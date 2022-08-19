package com.example.demo.repositories;

import com.example.demo.models.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    List<Favorite> findByUserId(int userId);

    void deleteByUserIdAndMovieId(int userId,String movieId);

    Favorite findByUserIdAndMovieId(int userId, String fakeMovieId);

}