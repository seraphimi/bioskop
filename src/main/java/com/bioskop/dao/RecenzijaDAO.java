package com.bioskop.dao;

import com.bioskop.entity.Recenzija;
import com.bioskop.entity.Film;
import com.bioskop.entity.Korisnik;
import com.bioskop.repository.RecenzijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RecenzijaDAO {
    
    @Autowired
    private RecenzijaRepository recenzijaRepository;
    
    public List<Recenzija> findAll() {
        return recenzijaRepository.findAll();
    }
    
    public Optional<Recenzija> findById(Long id) {
        return recenzijaRepository.findById(id);
    }
    
    public Recenzija save(Recenzija recenzija) {
        return recenzijaRepository.save(recenzija);
    }
    
    public void deleteById(Long id) {
        recenzijaRepository.deleteById(id);
    }
    
    public List<Recenzija> findByFilm(Film film) {
        return recenzijaRepository.findByFilm(film);
    }
    
    public List<Recenzija> findByKorisnik(Korisnik korisnik) {
        return recenzijaRepository.findByKorisnik(korisnik);
    }
    
    public List<Recenzija> findByFilmIdOrderByDatumDesc(Long filmId) {
        return recenzijaRepository.findByFilmIdOrderByDatumDesc(filmId);
    }
    
    public Double findAverageRatingByFilmId(Long filmId) {
        return recenzijaRepository.findAverageRatingByFilmId(filmId);
    }
    
    public boolean existsByFilmAndKorisnik(Film film, Korisnik korisnik) {
        return recenzijaRepository.existsByFilmAndKorisnik(film, korisnik);
    }
    
    public boolean existsById(Long id) {
        return recenzijaRepository.existsById(id);
    }
}