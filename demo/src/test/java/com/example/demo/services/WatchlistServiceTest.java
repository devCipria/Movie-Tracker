package com.example.demo.services;

import com.example.demo.DemoApplication;
import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.models.Watchlist;
import com.example.demo.repositories.WatchlistRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = DemoApplication.class)
@TestPropertySource("classpath:application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WatchlistServiceTest {
    @Autowired
    private WatchlistService watchlistService;

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Test
    @Order(1)
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
    @Order(2)
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
    @Order(3)
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
    @Order(4)
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
    @Order(5)
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
}
