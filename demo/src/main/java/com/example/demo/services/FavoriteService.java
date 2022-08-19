package com.example.demo.services;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.models.Favorite;
import com.example.demo.repositories.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * FavoriteService
 */
@Service
@Transactional
public class FavoriteService {

    @Autowired
    FavoriteRepository favoriteRepository;

    /**
     * @param userId id of user
     * @return retunr slist of favorite objects
     */
    public List<Favorite> getFavoritesByUser(int userId) {
        return favoriteRepository.findByUserId(userId);
    }

    /**
     * @param favorite adds a favorite object into the db
     */
//    public Favorite addToFavorites(Favorite favorite) {
//        return favoriteRepository.save(favorite);
//    }
    public void addToFavorites(Favorite favorite) {
        int userId = favorite.getUserId();
        String movieId = favorite.getMovieId();
        Favorite duplicate = favoriteRepository.findByUserIdAndMovieId(userId, movieId);
        if (duplicate == null) {
            favoriteRepository.save(favorite);
        } else {
            throw new DuplicateEntityException("Movie already in your Favorites");
        }
    }

    /**
     * @param userId user id
     * @param movieId movoie id
     */
    public void deleteFromFavorites(int userId,String movieId) {
        favoriteRepository.deleteByUserIdAndMovieId(userId,movieId);
    }
}