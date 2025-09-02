package com.bioskop.dao;

import com.bioskop.entity.Film;
import com.bioskop.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class FilmDAO {
    
    @Autowired
    private FilmRepository filmRepository;
    
    public List<Film> findAll() {
        return filmRepository.findAll();
    }
    
    public Optional<Film> findById(Long id) {
        return filmRepository.findById(id);
    }
    
    public Film save(Film film) {
        return filmRepository.save(film);
    }
    
    public void deleteById(Long id) {
        filmRepository.deleteById(id);
    }
    
    public List<Film> findByZanr(String zanr) {
        return filmRepository.findByZanrContainingIgnoreCase(zanr);
    }
    
    public List<Film> findByNaziv(String naziv) {
        return filmRepository.findByNazivContainingIgnoreCase(naziv);
    }
    
    public List<Film> findByReditelj(String reditelj) {
        return filmRepository.findByReditelj(reditelj);
    }
    
    public List<Film> findByMinOcena(BigDecimal minOcena) {
        return filmRepository.findByOcenaGreaterThanEqual(minOcena);
    }
    
    public List<Film> findByTrajanjeRange(Integer minTrajanje, Integer maxTrajanje) {
        return filmRepository.findByTrajanjeRange(minTrajanje, maxTrajanje);
    }
    
    public boolean existsById(Long id) {
        return filmRepository.existsById(id);
    }
}