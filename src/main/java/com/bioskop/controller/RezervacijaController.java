package com.bioskop.controller;

import com.bioskop.entity.Rezervacija;
import com.bioskop.service.RezervacijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rezervacije")
public class RezervacijaController {
    
    @Autowired
    private RezervacijaService rezervacijaService;
    
    @GetMapping
    public ResponseEntity<List<Rezervacija>> getAllRezervacije() {
        List<Rezervacija> rezervacije = rezervacijaService.getAllRezervacije();
        return ResponseEntity.ok(rezervacije);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Rezervacija> getRezervacijaById(@PathVariable Long id) {
        Optional<Rezervacija> rezervacija = rezervacijaService.getRezervacijaById(id);
        if (rezervacija.isPresent()) {
            return ResponseEntity.ok(rezervacija.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<Rezervacija> createRezervacija(@Valid @RequestBody Rezervacija rezervacija) {
        try {
            Rezervacija savedRezervacija = rezervacijaService.saveRezervacija(rezervacija);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRezervacija);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Rezervacija> updateRezervacija(@PathVariable Long id, @Valid @RequestBody Rezervacija rezervacija) {
        try {
            Rezervacija updatedRezervacija = rezervacijaService.updateRezervacija(id, rezervacija);
            return ResponseEntity.ok(updatedRezervacija);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRezervacija(@PathVariable Long id) {
        try {
            rezervacijaService.deleteRezervacija(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/korisnik/{korisnikId}")
    public ResponseEntity<List<Rezervacija>> getRezervacijeByKorisnik(@PathVariable Long korisnikId) {
        List<Rezervacija> rezervacije = rezervacijaService.getRezervacijeByKorisnik(korisnikId);
        return ResponseEntity.ok(rezervacije);
    }
    
    @GetMapping("/film/{filmId}")
    public ResponseEntity<List<Rezervacija>> getRezervacijeByFilm(@PathVariable Long filmId) {
        try {
            List<Rezervacija> rezervacije = rezervacijaService.getRezervacijeByFilm(filmId);
            return ResponseEntity.ok(rezervacije);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/projekcija/{projekcijaId}")
    public ResponseEntity<List<Rezervacija>> getRezervacijeByProjekcija(@PathVariable Long projekcijaId) {
        try {
            List<Rezervacija> rezervacije = rezervacijaService.getRezervacijeByProjekcija(projekcijaId);
            return ResponseEntity.ok(rezervacije);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}/kupovina")
    public ResponseEntity<Rezervacija> kupovinaKarte(@PathVariable Long id) {
        try {
            Rezervacija rezervacija = rezervacijaService.kupovina(id);
            return ResponseEntity.ok(rezervacija);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}