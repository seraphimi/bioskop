package com.bioskop.dao;

import com.bioskop.entity.Projekcija;
import com.bioskop.entity.Film;
import com.bioskop.repository.ProjekcijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ProjekcijaDAO {
    
    @Autowired
    private ProjekcijaRepository projekcijaRepository;
    
    public List<Projekcija> findAll() {
        return projekcijaRepository.findAll();
    }
    
    public Optional<Projekcija> findById(Long id) {
        return projekcijaRepository.findById(id);
    }
    
    public Projekcija save(Projekcija projekcija) {
        return projekcijaRepository.save(projekcija);
    }
    
    public void deleteById(Long id) {
        projekcijaRepository.deleteById(id);
    }
    
    public List<Projekcija> findByFilm(Film film) {
        return projekcijaRepository.findByFilm(film);
    }
    
    public List<Projekcija> findBySala(Integer sala) {
        return projekcijaRepository.findBySala(sala);
    }
    
    public List<Projekcija> findByDatumVremeRange(LocalDateTime startDate, LocalDateTime endDate) {
        return projekcijaRepository.findByDatumVremeRange(startDate, endDate);
    }
    
    public List<Projekcija> findAvailableProjections() {
        return projekcijaRepository.findAvailableProjections();
    }
    
    public List<Projekcija> findUpcomingProjectionsByFilm(Long filmId) {
        return projekcijaRepository.findUpcomingProjectionsByFilm(filmId, LocalDateTime.now());
    }
    
    public List<Projekcija> findUpcomingProjections() {
        return projekcijaRepository.findUpcomingProjections(LocalDateTime.now());
    }
    
    public boolean existsById(Long id) {
        return projekcijaRepository.existsById(id);
    }
}