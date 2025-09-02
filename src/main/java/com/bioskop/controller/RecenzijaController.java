package com.bioskop.controller;

import com.bioskop.entity.Recenzija;
import com.bioskop.service.RecenzijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recenzije")
@CrossOrigin(origins = "*")
public class RecenzijaController {
    
    @Autowired
    private RecenzijaService recenzijaService;
    
    @GetMapping
    public ResponseEntity<List<Recenzija>> getAllRecenzije() {
        List<Recenzija> recenzije = recenzijaService.getAllRecenzije();
        return ResponseEntity.ok(recenzije);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Recenzija> getRecenzijaById(@PathVariable Long id) {
        Optional<Recenzija> recenzija = recenzijaService.getRecenzijaById(id);
        if (recenzija.isPresent()) {
            return ResponseEntity.ok(recenzija.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<Recenzija> createRecenzija(@Valid @RequestBody Recenzija recenzija) {
        try {
            Recenzija savedRecenzija = recenzijaService.saveRecenzija(recenzija);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRecenzija);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Recenzija> updateRecenzija(@PathVariable Long id, @Valid @RequestBody Recenzija recenzija) {
        try {
            Recenzija updatedRecenzija = recenzijaService.updateRecenzija(id, recenzija);
            return ResponseEntity.ok(updatedRecenzija);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecenzija(@PathVariable Long id) {
        try {
            recenzijaService.deleteRecenzija(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/film/{filmId}")
    public ResponseEntity<List<Recenzija>> getRecenzijeByFilm(@PathVariable Long filmId) {
        try {
            List<Recenzija> recenzije = recenzijaService.getRecenzijeByFilm(filmId);
            return ResponseEntity.ok(recenzije);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/korisnik/{korisnikId}")
    public ResponseEntity<List<Recenzija>> getRecenzijeByKorisnik(@PathVariable Long korisnikId) {
        try {
            List<Recenzija> recenzije = recenzijaService.getRecenzijeByKorisnik(korisnikId);
            return ResponseEntity.ok(recenzije);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/film/{filmId}/prosecna-ocena")
    public ResponseEntity<Double> getAverageRatingForFilm(@PathVariable Long filmId) {
        Double averageRating = recenzijaService.getAverageRatingForFilm(filmId);
        if (averageRating != null) {
            return ResponseEntity.ok(averageRating);
        }
        return ResponseEntity.ok(0.0);
    }
}