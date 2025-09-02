package com.bioskop.service;

import org.springframework.stereotype.Service;

/**
 * Service class for managing ticket bookings.
 * Methods in this class will be intercepted by the AOP logging aspect.
 */
@Service
public class TicketService {
    
    private int availableSeats = 100;
    
    public String bookTicket(String customerName, int numberOfTickets) {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty");
        }
        
        if (numberOfTickets <= 0) {
            throw new IllegalArgumentException("Number of tickets must be positive");
        }
        
        if (numberOfTickets > availableSeats) {
            throw new RuntimeException("Not enough available seats. Available: " + availableSeats);
        }
        
        availableSeats -= numberOfTickets;
        return String.format("Booked %d tickets for %s. Remaining seats: %d", 
                           numberOfTickets, customerName, availableSeats);
    }
    
    public int getAvailableSeats() {
        return availableSeats;
    }
    
    public void releaseSeats(int numberOfSeats) {
        if (numberOfSeats > 0) {
            availableSeats += numberOfSeats;
        }
    }
}