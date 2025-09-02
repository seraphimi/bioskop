package com.bioskop.dao;

import com.bioskop.entity.Rezervacija;
import com.bioskop.entity.Korisnik;
import com.bioskop.entity.Film;
import com.bioskop.entity.Projekcija;
import com.bioskop.repository.RezervacijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RezervacijaDAO {
    
    @Autowired
    private RezervacijaRepository rezervacijaRepository;
    
    public List<Rezervacija> findAll() {
        return rezervacijaRepository.findAll();
    }
    
    public Optional<Rezervacija> findById(Long id) {
        return rezervacijaRepository.findById(id);
    }
    
    public Rezervacija save(Rezervacija rezervacija) {
        return rezervacijaRepository.save(rezervacija);
    }
    
    public void deleteById(Long id) {
        rezervacijaRepository.deleteById(id);
    }
    
    public List<Rezervacija> findByKorisnik(Korisnik korisnik) {
        return rezervacijaRepository.findByKorisnik(korisnik);
    }
    
    public List<Rezervacija> findByFilm(Film film) {
        return rezervacijaRepository.findByFilm(film);
    }
    
    public List<Rezervacija> findByProjekcija(Projekcija projekcija) {
        return rezervacijaRepository.findByProjekcija(projekcija);
    }
    
    public List<Rezervacija> findByStatus(Rezervacija.StatusRezervacije status) {
        return rezervacijaRepository.findByStatus(status);
    }
    
    public List<Rezervacija> findByKorisnikIdOrderByDatumDesc(Long korisnikId) {
        return rezervacijaRepository.findByKorisnikIdOrderByDatumDesc(korisnikId);
    }
    
    public Long countByProjekcijaId(Long projekcijaId) {
        return rezervacijaRepository.countByProjekcijaId(projekcijaId);
    }
    
    public boolean existsById(Long id) {
        return rezervacijaRepository.existsById(id);
    }
}