package com.bioskop.service;

import com.bioskop.dao.ProjekcijaDAO;
import com.bioskop.dao.FilmDAO;
import com.bioskop.entity.Projekcija;
import com.bioskop.entity.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjekcijaService {
    
    @Autowired
    private ProjekcijaDAO projekcijaDAO;
    
    @Autowired
    private FilmDAO filmDAO;
    
    public List<Projekcija> getAllProjekcije() {
        return projekcijaDAO.findAll();
    }
    
    public Optional<Projekcija> getProjekcijaById(Long id) {
        return projekcijaDAO.findById(id);
    }
    
    public Projekcija saveProjekcija(Projekcija projekcija) {
        // Validate that the film exists
        if (projekcija.getFilm() != null && projekcija.getFilm().getId() != null) {
            Optional<Film> film = filmDAO.findById(projekcija.getFilm().getId());
            if (film.isEmpty()) {
                throw new RuntimeException("Film sa ID " + projekcija.getFilm().getId() + " nije pronađen");
            }
            projekcija.setFilm(film.get());
        }
        
        // Validate projection time is in the future
        if (projekcija.getDatumVreme().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Datum i vreme projekcije moraju biti u budućnosti");
        }
        
        return projekcijaDAO.save(projekcija);
    }
    
    public Projekcija updateProjekcija(Long id, Projekcija updatedProjekcija) {
        Optional<Projekcija> existingProjekcija = projekcijaDAO.findById(id);
        if (existingProjekcija.isPresent()) {
            Projekcija projekcija = existingProjekcija.get();
            projekcija.setDatumVreme(updatedProjekcija.getDatumVreme());
            projekcija.setSala(updatedProjekcija.getSala());
            projekcija.setUkupnaSedista(updatedProjekcija.getUkupnaSedista());
            projekcija.setDostupnaSedista(updatedProjekcija.getDostupnaSedista());
            
            if (updatedProjekcija.getFilm() != null) {
                projekcija.setFilm(updatedProjekcija.getFilm());
            }
            
            return projekcijaDAO.save(projekcija);
        }
        throw new RuntimeException("Projekcija sa ID " + id + " nije pronađena");
    }
    
    public void deleteProjekcija(Long id) {
        if (!projekcijaDAO.existsById(id)) {
            throw new RuntimeException("Projekcija sa ID " + id + " nije pronađena");
        }
        projekcijaDAO.deleteById(id);
    }
    
    public List<Projekcija> getProjekcijeByFilm(Long filmId) {
        Optional<Film> film = filmDAO.findById(filmId);
        if (film.isEmpty()) {
            throw new RuntimeException("Film sa ID " + filmId + " nije pronađen");
        }
        return projekcijaDAO.findByFilm(film.get());
    }
    
    public List<Projekcija> getProjekcijeByDatumRange(LocalDateTime startDate, LocalDateTime endDate) {
        return projekcijaDAO.findByDatumVremeRange(startDate, endDate);
    }
    
    public List<Projekcija> getAvailableProjekcije() {
        return projekcijaDAO.findAvailableProjections();
    }
    
    public List<Projekcija> getUpcomingProjekcije() {
        return projekcijaDAO.findUpcomingProjections();
    }
    
    public List<Projekcija> getUpcomingProjekcijeByFilm(Long filmId) {
        return projekcijaDAO.findUpcomingProjectionsByFilm(filmId);
    }
    
    public boolean updateAvailableSeats(Long projekcijaId, Integer reservedSeats) {
        Optional<Projekcija> projekcijaOpt = projekcijaDAO.findById(projekcijaId);
        if (projekcijaOpt.isPresent()) {
            Projekcija projekcija = projekcijaOpt.get();
            if (projekcija.getDostupnaSedista() >= reservedSeats) {
                projekcija.setDostupnaSedista(projekcija.getDostupnaSedista() - reservedSeats);
                projekcijaDAO.save(projekcija);
                return true;
            }
        }
        return false;
    }
}