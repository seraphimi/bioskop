package com.bioskop.service;

import com.bioskop.dao.KorisnikDAO;
import com.bioskop.entity.Korisnik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class KorisnikService {
    
    @Autowired
    private KorisnikDAO korisnikDAO;
    
    public List<Korisnik> getAllKorisnici() {
        return korisnikDAO.findAll();
    }
    
    public Optional<Korisnik> getKorisnikById(Long id) {
        return korisnikDAO.findById(id);
    }
    
    public Korisnik saveKorisnik(Korisnik korisnik) {
        // Check if email already exists
        if (korisnikDAO.existsByEmail(korisnik.getEmail())) {
            throw new RuntimeException("Korisnik sa email adresom " + korisnik.getEmail() + " već postoji");
        }
        return korisnikDAO.save(korisnik);
    }
    
    public Korisnik updateKorisnik(Long id, Korisnik updatedKorisnik) {
        Optional<Korisnik> existingKorisnik = korisnikDAO.findById(id);
        if (existingKorisnik.isPresent()) {
            Korisnik korisnik = existingKorisnik.get();
            
            // Check if email is being changed and if new email already exists
            if (!korisnik.getEmail().equals(updatedKorisnik.getEmail()) && 
                korisnikDAO.existsByEmail(updatedKorisnik.getEmail())) {
                throw new RuntimeException("Korisnik sa email adresom " + updatedKorisnik.getEmail() + " već postoji");
            }
            
            korisnik.setIme(updatedKorisnik.getIme());
            korisnik.setPrezime(updatedKorisnik.getPrezime());
            korisnik.setEmail(updatedKorisnik.getEmail());
            return korisnikDAO.save(korisnik);
        }
        throw new RuntimeException("Korisnik sa ID " + id + " nije pronađen");
    }
    
    public void deleteKorisnik(Long id) {
        if (!korisnikDAO.existsById(id)) {
            throw new RuntimeException("Korisnik sa ID " + id + " nije pronađen");
        }
        korisnikDAO.deleteById(id);
    }
    
    public Optional<Korisnik> getKorisnikByEmail(String email) {
        return korisnikDAO.findByEmail(email);
    }
    
    public boolean existsByEmail(String email) {
        return korisnikDAO.existsByEmail(email);
    }
}