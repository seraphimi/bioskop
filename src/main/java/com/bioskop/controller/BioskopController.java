package com.bioskop.controller;

import com.bioskop.service.MovieService;
import com.bioskop.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for the bioskop application.
 * Methods in this class will be intercepted by the AOP logging aspect.
 */
@RestController
@RequestMapping("/api")
public class BioskopController {
    
    @Autowired
    private MovieService movieService;
    
    @Autowired
    private TicketService ticketService;
    
    @PostMapping("/movies")
    public ResponseEntity<String> addMovie(@RequestBody String movieTitle) {
        try {
            String result = movieService.addMovie(movieTitle);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/movies")
    public ResponseEntity<List<String>> getAllMovies() {
        List<String> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }
    
    @PostMapping("/tickets/book")
    public ResponseEntity<String> bookTicket(@RequestParam String customerName, 
                                            @RequestParam int numberOfTickets) {
        try {
            String result = ticketService.bookTicket(customerName, numberOfTickets);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/tickets/available")
    public ResponseEntity<Integer> getAvailableSeats() {
        int availableSeats = ticketService.getAvailableSeats();
        return ResponseEntity.ok(availableSeats);
    }
}