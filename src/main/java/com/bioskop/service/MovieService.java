package com.bioskop.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

/**
 * Service class for managing movies in the bioskop application.
 * Methods in this class will be intercepted by the AOP logging aspect.
 */
@Service
public class MovieService {
    
    private List<String> movies = new ArrayList<>();
    
    public String addMovie(String movieTitle) {
        if (movieTitle == null || movieTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Movie title cannot be null or empty");
        }
        movies.add(movieTitle);
        return "Movie added: " + movieTitle;
    }
    
    public List<String> getAllMovies() {
        return new ArrayList<>(movies);
    }
    
    public String getMovieByIndex(int index) {
        if (index < 0 || index >= movies.size()) {
            throw new IndexOutOfBoundsException("Invalid movie index: " + index);
        }
        return movies.get(index);
    }
    
    public boolean removeMovie(String movieTitle) {
        return movies.remove(movieTitle);
    }
    
    public int getMovieCount() {
        return movies.size();
    }
}