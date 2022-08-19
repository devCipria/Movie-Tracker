package com.example.demo.services;

import com.example.demo.DemoApplication;
import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.models.Favorite;
import com.example.demo.repositories.FavoriteRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DemoApplication.class)
@TestPropertySource("classpath:application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FavoriteServiceTest {
    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Test
    @Order(1)
    public void verify_getFavoritesByUser() {
        int userId = 3;

        // Verify that userId 3 does not have any favorites
        List<Favorite> favorites = favoriteService.getFavoritesByUser(userId);
        assertTrue(favorites.isEmpty());

        // Add a record to the favorites table for userId 3
        favoriteService.addToFavorites(new Favorite(3, "fakeMovieId"));

        // Retrieve the list of favorites from user 3
        favorites = favoriteService.getFavoritesByUser(userId);
        assertNotNull(favorites);
        assertEquals(1, favorites.size());
        assertEquals(3, favorites.get(0).getUserId());
        assertEquals("fakeMovieId", favorites.get(0).getMovieId());

        // Delete the favorite from userId 3
        favoriteService.deleteFromFavorites(3, "fakeMovieId");
    }

    @Test
    @Order(2)
    public void verify_addToFavorites() {

        // Verifies that the favorites table does not include a record with a userId=1 and movieId='fakeMovieId'
        Favorite fav = favoriteRepository.findByUserIdAndMovieId(1, "fakeMovieId");
        assertNull(fav);

        // Add a record to the favorites table with a userId=1 and movieId='fakeMovieId'
        fav = new Favorite(1, "fakeMovieId");
        favoriteService.addToFavorites(fav);

        // Verifies that favoriteService.addToFavorites has added the record to the favorites table
        Favorite testFav = favoriteRepository.findByUserIdAndMovieId(1, "fakeMovieId");
        assertEquals(fav.getId(), testFav.getId());
        assertEquals(fav.getMovieId(), testFav.getMovieId());
    }

    @Test
    @Order(3)
    public void verify_deleteFromFavorites() {
        // Verifies that there exists a record in the favorites table with a userId=1 and movieId='fakeMovieId'
        Favorite fav = favoriteRepository.findByUserIdAndMovieId(1, "fakeMovieId");
        assertEquals(1, fav.getUserId());
        assertEquals("fakeMovieId", fav.getMovieId());

        // Deletes the record in the favorites table with a userId=1 and movieId='fakeMovieId'
        favoriteService.deleteFromFavorites(1, "fakeMovieId");

        // Verifies that the favorites table does not include a record with a userId=1 and movieId='fakeMovieId'
        Favorite testFav = favoriteRepository.findByUserIdAndMovieId(1, "fakeMovieId");
        assertNull(testFav);
    }

    @Test
    @Order(4)
    public void verify_addToFavoritesThrowsException() {
        try {
            // Add a record to the favorites table with a userId=3 and movieId='fakeMovieId'
            Favorite fav = new Favorite(3, "fakeMovieId");
            favoriteService.addToFavorites(fav);

            // Add the same movie to userId 3's favorites
            Throwable exception = assertThrows(DuplicateEntityException.class, () -> favoriteService.addToFavorites(fav));
            assertEquals("Movie already in your Favorites", exception.getMessage());
        } finally{
            // clean up - delete fav from favorites table
            favoriteService.deleteFromFavorites(3, "fakeMovieId");
        }
    }
}
