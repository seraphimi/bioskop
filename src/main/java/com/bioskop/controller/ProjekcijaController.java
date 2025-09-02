package com.bioskop.controller;

import com.bioskop.entity.Projekcija;
import com.bioskop.service.ProjekcijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projekcije")
@CrossOrigin(origins = "*")
public class ProjekcijaController {
    
    @Autowired
    private ProjekcijaService projekcijaService;
    
    @GetMapping
    public ResponseEntity<List<Projekcija>> getAllProjekcije() {
        List<Projekcija> projekcije = projekcijaService.getAllProjekcije();
        return ResponseEntity.ok(projekcije);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Projekcija> getProjekcijaById(@PathVariable Long id) {
        Optional<Projekcija> projekcija = projekcijaService.getProjekcijaById(id);
        if (projekcija.isPresent()) {
            return ResponseEntity.ok(projekcija.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<Projekcija> createProjekcija(@Valid @RequestBody Projekcija projekcija) {
        try {
            Projekcija savedProjekcija = projekcijaService.saveProjekcija(projekcija);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProjekcija);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Projekcija> updateProjekcija(@PathVariable Long id, @Valid @RequestBody Projekcija projekcija) {
        try {
            Projekcija updatedProjekcija = projekcijaService.updateProjekcija(id, projekcija);
            return ResponseEntity.ok(updatedProjekcija);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjekcija(@PathVariable Long id) {
        try {
            projekcijaService.deleteProjekcija(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/film/{filmId}")
    public ResponseEntity<List<Projekcija>> getProjekcijeByFilm(@PathVariable Long filmId) {
        try {
            List<Projekcija> projekcije = projekcijaService.getProjekcijeByFilm(filmId);
            return ResponseEntity.ok(projekcije);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/available")
    public ResponseEntity<List<Projekcija>> getAvailableProjekcije() {
        List<Projekcija> projekcije = projekcijaService.getAvailableProjekcije();
        return ResponseEntity.ok(projekcije);
    }
    
    @GetMapping("/upcoming")
    public ResponseEntity<List<Projekcija>> getUpcomingProjekcije() {
        List<Projekcija> projekcije = projekcijaService.getUpcomingProjekcije();
        return ResponseEntity.ok(projekcije);
    }
    
    @GetMapping("/upcoming/film/{filmId}")
    public ResponseEntity<List<Projekcija>> getUpcomingProjekcijeByFilm(@PathVariable Long filmId) {
        List<Projekcija> projekcije = projekcijaService.getUpcomingProjekcijeByFilm(filmId);
        return ResponseEntity.ok(projekcije);
    }
    
    @GetMapping("/search/datum")
    public ResponseEntity<List<Projekcija>> getProjekcijeByDatumRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Projekcija> projekcije = projekcijaService.getProjekcijeByDatumRange(startDate, endDate);
        return ResponseEntity.ok(projekcije);
    }
}