package com.bioskop.controller;

import com.bioskop.entity.Korisnik;
import com.bioskop.service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/korisnici")
@CrossOrigin(origins = "*")
public class KorisnikController {
    
    @Autowired
    private KorisnikService korisnikService;
    
    @GetMapping
    public ResponseEntity<List<Korisnik>> getAllKorisnici() {
        List<Korisnik> korisnici = korisnikService.getAllKorisnici();
        return ResponseEntity.ok(korisnici);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Korisnik> getKorisnikById(@PathVariable Long id) {
        Optional<Korisnik> korisnik = korisnikService.getKorisnikById(id);
        if (korisnik.isPresent()) {
            return ResponseEntity.ok(korisnik.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<Korisnik> createKorisnik(@Valid @RequestBody Korisnik korisnik) {
        try {
            Korisnik savedKorisnik = korisnikService.saveKorisnik(korisnik);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedKorisnik);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Korisnik> updateKorisnik(@PathVariable Long id, @Valid @RequestBody Korisnik korisnik) {
        try {
            Korisnik updatedKorisnik = korisnikService.updateKorisnik(id, korisnik);
            return ResponseEntity.ok(updatedKorisnik);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKorisnik(@PathVariable Long id) {
        try {
            korisnikService.deleteKorisnik(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/search/email")
    public ResponseEntity<Korisnik> getKorisnikByEmail(@RequestParam String email) {
        Optional<Korisnik> korisnik = korisnikService.getKorisnikByEmail(email);
        if (korisnik.isPresent()) {
            return ResponseEntity.ok(korisnik.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/exists/email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        boolean exists = korisnikService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
}