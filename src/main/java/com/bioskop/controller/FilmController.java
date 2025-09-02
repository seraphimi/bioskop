package com.bioskop.controller;

import com.bioskop.entity.Film;
import com.bioskop.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/filmovi")
@CrossOrigin(origins = "*")
public class FilmController {
    
    @Autowired
    private FilmService filmService;
    
    @GetMapping
    public ResponseEntity<List<Film>> getAllFilmovi() {
        List<Film> filmovi = filmService.getAllFilmovi();
        return ResponseEntity.ok(filmovi);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable Long id) {
        Optional<Film> film = filmService.getFilmById(id);
        if (film.isPresent()) {
            return ResponseEntity.ok(film.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<Film> createFilm(@Valid @RequestBody Film film) {
        try {
            Film savedFilm = filmService.saveFilm(film);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFilm);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Film> updateFilm(@PathVariable Long id, @Valid @RequestBody Film film) {
        try {
            Film updatedFilm = filmService.updateFilm(id, film);
            return ResponseEntity.ok(updatedFilm);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable Long id) {
        try {
            filmService.deleteFilm(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/search/zanr")
    public ResponseEntity<List<Film>> getFilmoviByZanr(@RequestParam String zanr) {
        List<Film> filmovi = filmService.getFilmoviByZanr(zanr);
        return ResponseEntity.ok(filmovi);
    }
    
    @GetMapping("/search/naziv")
    public ResponseEntity<List<Film>> getFilmoviByNaziv(@RequestParam String naziv) {
        List<Film> filmovi = filmService.getFilmoviByNaziv(naziv);
        return ResponseEntity.ok(filmovi);
    }
    
    @GetMapping("/search/reditelj")
    public ResponseEntity<List<Film>> getFilmoviByReditelj(@RequestParam String reditelj) {
        List<Film> filmovi = filmService.getFilmoviByReditelj(reditelj);
        return ResponseEntity.ok(filmovi);
    }
    
    @GetMapping("/search/ocena")
    public ResponseEntity<List<Film>> getFilmoviByMinOcena(@RequestParam BigDecimal minOcena) {
        List<Film> filmovi = filmService.getFilmoviByMinOcena(minOcena);
        return ResponseEntity.ok(filmovi);
    }
    
    @GetMapping("/search/trajanje")
    public ResponseEntity<List<Film>> getFilmoviByTrajanjeRange(
            @RequestParam Integer minTrajanje, 
            @RequestParam Integer maxTrajanje) {
        List<Film> filmovi = filmService.getFilmoviByTrajanjeRange(minTrajanje, maxTrajanje);
        return ResponseEntity.ok(filmovi);
    }
}