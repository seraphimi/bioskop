package com.bioskop.service;

import com.bioskop.dao.FilmDAO;
import com.bioskop.entity.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FilmService {
    
    @Autowired
    private FilmDAO filmDAO;
    
    public List<Film> getAllFilmovi() {
        return filmDAO.findAll();
    }
    
    public Optional<Film> getFilmById(Long id) {
        return filmDAO.findById(id);
    }
    
    public Film saveFilm(Film film) {
        return filmDAO.save(film);
    }
    
    public Film updateFilm(Long id, Film updatedFilm) {
        Optional<Film> existingFilm = filmDAO.findById(id);
        if (existingFilm.isPresent()) {
            Film film = existingFilm.get();
            film.setNaziv(updatedFilm.getNaziv());
            film.setZanr(updatedFilm.getZanr());
            film.setTrajanje(updatedFilm.getTrajanje());
            film.setReditelj(updatedFilm.getReditelj());
            film.setGlumci(updatedFilm.getGlumci());
            film.setOcena(updatedFilm.getOcena());
            film.setOpis(updatedFilm.getOpis());
            return filmDAO.save(film);
        }
        throw new RuntimeException("Film sa ID " + id + " nije pronađen");
    }
    
    public void deleteFilm(Long id) {
        if (!filmDAO.existsById(id)) {
            throw new RuntimeException("Film sa ID " + id + " nije pronađen");
        }
        filmDAO.deleteById(id);
    }
    
    public List<Film> getFilmoviByZanr(String zanr) {
        return filmDAO.findByZanr(zanr);
    }
    
    public List<Film> getFilmoviByNaziv(String naziv) {
        return filmDAO.findByNaziv(naziv);
    }
    
    public List<Film> getFilmoviByReditelj(String reditelj) {
        return filmDAO.findByReditelj(reditelj);
    }
    
    public List<Film> getFilmoviByMinOcena(BigDecimal minOcena) {
        return filmDAO.findByMinOcena(minOcena);
    }
    
    public List<Film> getFilmoviByTrajanjeRange(Integer minTrajanje, Integer maxTrajanje) {
        return filmDAO.findByTrajanjeRange(minTrajanje, maxTrajanje);
    }
}