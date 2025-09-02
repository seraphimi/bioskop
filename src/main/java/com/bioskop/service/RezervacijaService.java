package com.bioskop.service;

import com.bioskop.dao.RezervacijaDAO;
import com.bioskop.dao.FilmDAO;
import com.bioskop.dao.ProjekcijaDAO;
import com.bioskop.dao.KorisnikDAO;
import com.bioskop.entity.Rezervacija;
import com.bioskop.entity.Film;
import com.bioskop.entity.Projekcija;
import com.bioskop.entity.Korisnik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RezervacijaService {
    
    @Autowired
    private RezervacijaDAO rezervacijaDAO;
    
    @Autowired
    private FilmDAO filmDAO;
    
    @Autowired
    private ProjekcijaDAO projekcijaDAO;
    
    @Autowired
    private KorisnikDAO korisnikDAO;
    
    @Autowired
    private ProjekcijaService projekcijaService;
    
    public List<Rezervacija> getAllRezervacije() {
        return rezervacijaDAO.findAll();
    }
    
    public Optional<Rezervacija> getRezervacijaById(Long id) {
        return rezervacijaDAO.findById(id);
    }
    
    public Rezervacija saveRezervacija(Rezervacija rezervacija) {
        // Validate entities exist
        validateRezervacija(rezervacija);
        
        // Check seat availability
        Optional<Projekcija> projekcijaOpt = projekcijaDAO.findById(rezervacija.getProjekcija().getId());
        if (projekcijaOpt.isEmpty()) {
            throw new RuntimeException("Projekcija nije pronađena");
        }
        
        Projekcija projekcija = projekcijaOpt.get();
        if (projekcija.getDostupnaSedista() < rezervacija.getOdabranaSedista()) {
            throw new RuntimeException("Nema dovoljno dostupnih sedišta. Dostupno: " + 
                                     projekcija.getDostupnaSedista() + ", Potrebno: " + rezervacija.getOdabranaSedista());
        }
        
        // Calculate total price (assuming 500 RSD per seat)
        BigDecimal cenaPoSedistu = new BigDecimal("500.00");
        BigDecimal ukupnaCena = cenaPoSedistu.multiply(new BigDecimal(rezervacija.getOdabranaSedista()));
        rezervacija.setUkupnaCena(ukupnaCena);
        
        // Save reservation
        Rezervacija savedRezervacija = rezervacijaDAO.save(rezervacija);
        
        // Update available seats
        projekcijaService.updateAvailableSeats(projekcija.getId(), rezervacija.getOdabranaSedista());
        
        return savedRezervacija;
    }
    
    public Rezervacija updateRezervacija(Long id, Rezervacija updatedRezervacija) {
        Optional<Rezervacija> existingRezervacija = rezervacijaDAO.findById(id);
        if (existingRezervacija.isPresent()) {
            Rezervacija rezervacija = existingRezervacija.get();
            
            // Only allow status updates for existing reservations
            rezervacija.setStatus(updatedRezervacija.getStatus());
            
            return rezervacijaDAO.save(rezervacija);
        }
        throw new RuntimeException("Rezervacija sa ID " + id + " nije pronađena");
    }
    
    public void deleteRezervacija(Long id) {
        Optional<Rezervacija> rezervacijaOpt = rezervacijaDAO.findById(id);
        if (rezervacijaOpt.isEmpty()) {
            throw new RuntimeException("Rezervacija sa ID " + id + " nije pronađena");
        }
        
        Rezervacija rezervacija = rezervacijaOpt.get();
        
        // Return seats to available pool if reservation is being deleted
        Optional<Projekcija> projekcijaOpt = projekcijaDAO.findById(rezervacija.getProjekcija().getId());
        if (projekcijaOpt.isPresent()) {
            Projekcija projekcija = projekcijaOpt.get();
            projekcija.setDostupnaSedista(projekcija.getDostupnaSedista() + rezervacija.getOdabranaSedista());
            projekcijaDAO.save(projekcija);
        }
        
        rezervacijaDAO.deleteById(id);
    }
    
    public List<Rezervacija> getRezervacijeByKorisnik(Long korisnikId) {
        return rezervacijaDAO.findByKorisnikIdOrderByDatumDesc(korisnikId);
    }
    
    public List<Rezervacija> getRezervacijeByFilm(Long filmId) {
        Optional<Film> film = filmDAO.findById(filmId);
        if (film.isEmpty()) {
            throw new RuntimeException("Film sa ID " + filmId + " nije pronađen");
        }
        return rezervacijaDAO.findByFilm(film.get());
    }
    
    public List<Rezervacija> getRezervacijeByProjekcija(Long projekcijaId) {
        Optional<Projekcija> projekcija = projekcijaDAO.findById(projekcijaId);
        if (projekcija.isEmpty()) {
            throw new RuntimeException("Projekcija sa ID " + projekcijaId + " nije pronađena");
        }
        return rezervacijaDAO.findByProjekcija(projekcija.get());
    }
    
    public Rezervacija kupovina(Long rezervacijaId) {
        Optional<Rezervacija> rezervacijaOpt = rezervacijaDAO.findById(rezervacijaId);
        if (rezervacijaOpt.isEmpty()) {
            throw new RuntimeException("Rezervacija sa ID " + rezervacijaId + " nije pronađena");
        }
        
        Rezervacija rezervacija = rezervacijaOpt.get();
        if (rezervacija.getStatus() != Rezervacija.StatusRezervacije.REZERVISANO) {
            throw new RuntimeException("Rezervacija mora biti u statusu REZERVISANO da bi se mogla kupiti");
        }
        
        rezervacija.setStatus(Rezervacija.StatusRezervacije.KUPLJENO);
        return rezervacijaDAO.save(rezervacija);
    }
    
    private void validateRezervacija(Rezervacija rezervacija) {
        if (rezervacija.getFilm() == null || rezervacija.getFilm().getId() == null) {
            throw new RuntimeException("Film je obavezan");
        }
        if (rezervacija.getProjekcija() == null || rezervacija.getProjekcija().getId() == null) {
            throw new RuntimeException("Projekcija je obavezna");
        }
        if (rezervacija.getKorisnik() == null || rezervacija.getKorisnik().getId() == null) {
            throw new RuntimeException("Korisnik je obavezan");
        }
        
        // Validate entities exist
        if (!filmDAO.existsById(rezervacija.getFilm().getId())) {
            throw new RuntimeException("Film sa ID " + rezervacija.getFilm().getId() + " nije pronađen");
        }
        if (!projekcijaDAO.existsById(rezervacija.getProjekcija().getId())) {
            throw new RuntimeException("Projekcija sa ID " + rezervacija.getProjekcija().getId() + " nije pronađena");
        }
        if (!korisnikDAO.existsById(rezervacija.getKorisnik().getId())) {
            throw new RuntimeException("Korisnik sa ID " + rezervacija.getKorisnik().getId() + " nije pronađen");
        }
    }
}