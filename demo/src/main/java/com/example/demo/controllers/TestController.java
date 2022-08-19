package com.example.demo.controllers;

import com.example.demo.services.OMDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  TestController
 *
 *  simply for dev testing
 */
@RestController
public class TestController {
    @Autowired
    private OMDBService s;

    /**
     * @return response entity
     */
    @GetMapping("/test")
    public ResponseEntity test(){
        System.out.println("dummy print test");
        s.getMoviesByTitle("avatar");
        s.getMovieById("tt1285016");
        return ResponseEntity.ok(s);
    }
}
