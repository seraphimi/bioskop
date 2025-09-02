package com.bioskop.demo;

import com.bioskop.service.MovieService;
import com.bioskop.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Demo application that showcases AOP logging functionality.
 * Run this to see the @After pointcuts in action.
 * Disabled during tests.
 */
@Component
@ConditionalOnProperty(name = "bioskop.demo.enabled", havingValue = "true", matchIfMissing = true)
public class AopLoggingDemo implements CommandLineRunner {

    @Autowired
    private MovieService movieService;

    @Autowired
    private TicketService ticketService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n=== AOP Logging Demo ===");
        System.out.println("Demonstrating @After pointcuts only (no @Before pointcuts)\n");

        // Demonstrate successful method executions
        System.out.println("1. Adding movies (successful operations):");
        movieService.addMovie("The Matrix");
        movieService.addMovie("Inception");
        movieService.addMovie("Interstellar");

        System.out.println("\n2. Getting movie count:");
        int movieCount = movieService.getMovieCount();
        System.out.println("Total movies: " + movieCount);

        System.out.println("\n3. Booking tickets (successful operation):");
        ticketService.bookTicket("John Doe", 3);

        System.out.println("\n4. Checking available seats:");
        int availableSeats = ticketService.getAvailableSeats();
        System.out.println("Available seats: " + availableSeats);

        // Demonstrate exception handling
        System.out.println("\n5. Demonstrating exception handling:");
        try {
            movieService.addMovie(null); // This will throw an exception
        } catch (Exception e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        try {
            ticketService.bookTicket("Jane Doe", 200); // This will throw an exception
        } catch (Exception e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        System.out.println("\n=== Demo Complete ===");
        System.out.println("Check the log output above to see the AOP logging in action!");
        System.out.println("Notice that logs appear AFTER method execution, using only @After pointcuts.");
    }
}