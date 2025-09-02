package com.bioskop;

import com.bioskop.service.MovieService;
import com.bioskop.service.TicketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class to validate AOP logging functionality.
 * The logging aspect should intercept method calls using @After pointcuts only.
 */
@SpringBootTest
@TestPropertySource(properties = {
    "logging.level.com.bioskop.aspect=DEBUG"
})
public class AopLoggingTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private TicketService ticketService;

    @Test
    public void testMovieServiceLogging() {
        // Test successful method execution - should trigger @AfterReturning
        String result = movieService.addMovie("The Matrix");
        assertEquals("Movie added: The Matrix", result);
        
        // Test method that returns a value - should trigger @AfterReturning
        int count = movieService.getMovieCount();
        assertEquals(1, count);
        
        // Test method that throws an exception - should trigger @AfterThrowing
        assertThrows(IllegalArgumentException.class, () -> {
            movieService.addMovie(null);
        });
    }

    @Test
    public void testTicketServiceLogging() {
        // Test successful ticket booking - should trigger @AfterReturning
        String result = ticketService.bookTicket("John Doe", 2);
        assertTrue(result.contains("Booked 2 tickets for John Doe"));
        
        // Test method that returns a value - should trigger @AfterReturning
        int availableSeats = ticketService.getAvailableSeats();
        assertEquals(98, availableSeats);
        
        // Test method that throws an exception - should trigger @AfterThrowing
        assertThrows(RuntimeException.class, () -> {
            ticketService.bookTicket("Jane Doe", 200); // More than available
        });
    }

    @Test
    public void testAopContextLoads() {
        // Verify that the Spring context loads correctly with AOP enabled
        assertNotNull(movieService);
        assertNotNull(ticketService);
    }
}