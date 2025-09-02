package com.bioskop.dao;

import com.bioskop.entity.Korisnik;
import com.bioskop.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class KorisnikDAO {
    
    @Autowired
    private KorisnikRepository korisnikRepository;
    
    public List<Korisnik> findAll() {
        return korisnikRepository.findAll();
    }
    
    public Optional<Korisnik> findById(Long id) {
        return korisnikRepository.findById(id);
    }
    
    public Korisnik save(Korisnik korisnik) {
        return korisnikRepository.save(korisnik);
    }
    
    public void deleteById(Long id) {
        korisnikRepository.deleteById(id);
    }
    
    public Optional<Korisnik> findByEmail(String email) {
        return korisnikRepository.findByEmail(email);
    }
    
    public boolean existsByEmail(String email) {
        return korisnikRepository.existsByEmail(email);
    }
    
    public boolean existsById(Long id) {
        return korisnikRepository.existsById(id);
    }
}