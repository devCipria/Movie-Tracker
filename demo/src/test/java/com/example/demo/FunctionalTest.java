package com.example.demo;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.models.*;
import com.example.demo.repositories.FavoriteRepository;
import com.example.demo.repositories.ReviewRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.WatchlistRepository;
import com.example.demo.services.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = DemoApplication.class)
@TestPropertySource("classpath:application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FunctionalTest {
    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private WatchlistService watchlistService;

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

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
    public void verify_getWatchlistByUserId() {
        int userId = 3;

        // Verify that userId 3 does not have any movies on their watchlist
        List<Watchlist> watchlists = watchlistService.getWatchlistByUserId(userId);
        assertTrue(watchlists.isEmpty());

        // Add a record to the watchlists table for userId 3
        watchlistService.addToWatchlist(new Watchlist("general", 3, "fakeMovieId", true));

        // Retrieve the list of watchlists from user 3
        watchlists = watchlistService.getWatchlistByUserId(userId);
        assertNotNull(watchlists);
        assertEquals(1, watchlists.size());
        assertEquals(3, watchlists.get(0).getUserId());
        assertEquals("fakeMovieId", watchlists.get(0).getMovieId());

        // Delete the watchlist from userId 3
        watchlistService.deleteWatchlistByUserIdAndMovieId(3, "fakeMovieId");
    }

    @Test
    @Order(5)
    public void verify_addToWatchlist() {
        // Verifies that the watchlists table does not include a record with a userId=1 and movieId='fakeMovieId'
        Watchlist watchlist = watchlistRepository.findByUserIdAndMovieId(1, "fakeMovieId");
        assertNull(watchlist);

        // Add a record to the watchlists table with a userId=1 and movieId='fakeMovieId'
        watchlist = new Watchlist("general", 1, "fakeMovieId", true);
        watchlistService.addToWatchlist(watchlist);

        // Verifies that watchlistService.addToWatchlist has added the record to the watchlists table
        Watchlist testWatchlist = watchlistRepository.findByUserIdAndMovieId(1, "fakeMovieId");
        assertEquals(watchlist.getId(), testWatchlist.getId());
        assertEquals(watchlist.getMovieId(), testWatchlist.getMovieId());
    }

    @Test
    @Order(6)
    public void verify_deleteWatchlistByUserIdAndMovieId() {
        // Verifies that there exists a record in the watchlists table with a userId=1 and movieId='fakeMovieId'
        Watchlist watchlist = watchlistRepository.findByUserIdAndMovieId(1, "fakeMovieId");
        assertEquals(1, watchlist.getUserId());
        assertEquals("fakeMovieId", watchlist.getMovieId());

        // Deletes the record in the watchlists table with a userId=1 and movieId='fakeMovieId'
        watchlistService.deleteWatchlistByUserIdAndMovieId(1, "fakeMovieId");

        // Verifies that the watchlists table does not include a record with a userId=1 and movieId='fakeMovieId'
        Watchlist testWatchlist = watchlistRepository.findByUserIdAndMovieId(1, "fakeMovieId");
        assertNull(testWatchlist);
    }

    @Test
    @Order(7)
    public void verify_createUser() {
        // Verify that a user with the username="testUser" and password="testPassword" does not exit in the users table
        User newUser = userService.getUserByUsername("testUser");
        assertNull(newUser);

        // Add a record to the users table with the username="testUser" and password="testPassword"
        newUser = new User("testUser", "testPassword");
        userService.createUser(newUser);

        // Verifies that userService.createUser method has added the record to the users table
        User retrievedUser = userService.getUserByUsername("testUser");
        assertEquals("testUser", retrievedUser.getUsername());
        assertEquals("testPassword", retrievedUser.getPassword());
    }

    @Test
    @Order(8)
    public void verify_deleteUser() {
        // Verifies that there exists a record in the users table with a username="testUser"
        User testUser = userService.getUserByUsername("testUser");
        assertEquals("testUser", testUser.getUsername());

        // Deletes the record in the users table with a username="testUser"
        userService.deleteUserByUsername("testUser");

        // Verifies that the users table does not include a record with a username="testUser"
        User nullUser = userService.getUserByUsername("testUser");
        assertNull(nullUser);
    }

    @Test
    @Order(9)
    public void verify_getUserById() {
        // Creates a user
        User user = new User("testUser", "testPassword");
        userService.createUser(user);

        // Retrieves a user from the DB with the max id
        int maxId = userRepository.findMaxId();
        User retrievedUser = userService.getUserById(maxId);

        assertEquals(maxId, retrievedUser.getId());
        assertEquals("testUser", retrievedUser.getUsername());
        assertEquals("testPassword", retrievedUser.getPassword());

        // deletes the user
        userService.deleteUserByUsername("testUser");
    }

    @Test
    @Order(10)
    public void verify_loadUserByNameThrowsException() {
        Throwable exception = assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername("null"));
        assertEquals("User Not Found", exception.getMessage());
    }

    @Test
    @Order(11)
    public void verify_getReviewsByUser() {
        int userId = 3;

        // Verify that userId 3 does not have any reviews
        List<Review> reviews = reviewService.getReviewsByUser(userId);
        assertTrue(reviews.isEmpty());

        // Add a record to the reviews table for userId 3
        reviewService.createReview(new Review(userId, "fakeMovieId", 3, "Great"));

        // Retrieve the list of reviews from user 3
        reviews = reviewService.getReviewsByUser(userId);
        assertNotNull(reviews);
        assertEquals(1, reviews.size());
        assertEquals(3, reviews.get(0).getUserId());
        assertEquals("fakeMovieId", reviews.get(0).getMovieId());

        // Delete the reviews from userId 3
        reviewService.deleteFromReviews(userId, "fakeMovieId");
    }

    @Test
    @Order(12)
    public void verify_createReview() {
        // Verifies that the reviews table does not include a record with a userId=1 and movieId='fakeMovieId'
        Review review = reviewRepository.findByUserIdAndMovieId(1, "fakeMovieId");
        assertNull(review);

        // Add a record to the reviews table with a userId=1 and movieId='fakeMovieId'
        review = new Review(1, "fakeMovieId", 3, "Great");
        reviewService.createReview(review);

        // Verifies that reviewService.createReview() has added the record to the reviews table
        Review retrievedReview = reviewRepository.findByUserIdAndMovieId(1, "fakeMovieId");
        assertEquals(review.getId(), retrievedReview.getId());
        assertEquals(review.getMovieId(), retrievedReview.getMovieId());
    }

    @Test
    @Order(13)
    public void verify_deleteFromReviews() {
        // Verifies that there exists a record in the reviews table with a userId=1 and movieId='fakeMovieId'
        Review review = reviewRepository.findByUserIdAndMovieId(1, "fakeMovieId");
        assertEquals(1, review.getUserId());
        assertEquals("fakeMovieId", review.getMovieId());

        // Deletes the record in the reviews table with a userId=1 and movieId='fakeMovieId'
        reviewService.deleteFromReviews(1, "fakeMovieId");

        // Verifies that the reviews table does not include a record with a userId=1 and movieId='fakeMovieId'
        Review retrievedReview = reviewRepository.findByUserIdAndMovieId(1, "fakeMovieId");
        assertNull(retrievedReview);
    }

    @Test
    @Order(14)
    public void verify_getWatchlistByUserIdAndMovieId() {
        int userId = 3;

        // Verify that userId 3 does not have any movies on their watchlist
        List<Watchlist> watchlists = watchlistService.getWatchlistByUserId(userId);
        assertTrue(watchlists.isEmpty());

        // Add a record to the watchlists table for userId 3 with the name general
        watchlistService.addToWatchlist(new Watchlist("general", 3, "fakeMovieId", true));

        // Retrieve the list of watchlists by userId 3 and name general
        watchlists = watchlistService.getWatchlistByUserIdAndName(3, "general");
        assertNotNull(watchlists);
        assertEquals(1, watchlists.size());
        assertEquals(3, watchlists.get(0).getUserId());
        assertEquals("general", watchlists.get(0).getName());

        // Delete the watchlist from userId 3
        watchlistService.deleteWatchlistByUserIdAndMovieId(3, "fakeMovieId");
    }

    @Test
    @Order(15)
    public void verify_CustomUserDetailsService() {
        User userJim = userService.getUserByUsername("jim");
        String username = userJim.getUsername();
        CustomUserDetails loadedUser = (CustomUserDetails) customUserDetailsService.loadUserByUsername(username);
        assertEquals(username, loadedUser.getUsername());
        assertEquals(userJim.getPassword(), loadedUser.getPassword());
    }

    @Test
    @Order(16)
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

    @Test
    @Order(17)
    public void verify_addToWatchlistThrowsException() {
        try {
            // Add a record to the watchlists table with a userId=3 and movieId='fakeMovieId'
            Watchlist watchlist = new Watchlist("general", 3, "fakeMovieId", true);
            watchlistService.addToWatchlist(watchlist);

            // Add the same movie to userId 3's watchlists
            Throwable exception = assertThrows(DuplicateEntityException.class, () -> watchlistService.addToWatchlist(watchlist));
            assertEquals("Movie already in your Watchlist", exception.getMessage());
        } finally{
            // clean up - delete watchlist from watchlists table
            watchlistService.deleteWatchlistByUserIdAndMovieId(3, "fakeMovieId");
        }
    }

    @Test
    @Order(18)
    public void verify_createReviewThrowsException() {
        try {
            // Add a record to the reviews table with a userId=3 and movieId='fakeMovieId'
            Review review = new Review( 3, "fakeMovieId", 1, "This is a review");
            reviewService.createReview(review);

            // Add the same movie review to userId 3's reviews
            Throwable exception = assertThrows(DuplicateEntityException.class, () -> reviewService.createReview(review));
            assertEquals("You have already reviewed this movie", exception.getMessage());
        } finally{
            // clean up - delete review from reviews table
            reviewService.deleteFromReviews(3, "fakeMovieId");
        }
    }


}
