package com.example.demo.repositories;

import com.example.demo.models.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Integer> {
    List<Watchlist> findByUserId(int userId);
    List<Watchlist> findByName(String name);
    List<Watchlist> findByUserIdAndName(int userId, String name);
    Optional<Watchlist> findByMovieId(String name);
    Optional<Watchlist> findById(int id);
    void deleteByUserIdAndMovieId(int userId,String movieId);

    Watchlist findByUserIdAndMovieId(int userId, String fakeMovieId);
}