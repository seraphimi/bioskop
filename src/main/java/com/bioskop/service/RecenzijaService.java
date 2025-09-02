package com.bioskop.service;

import com.bioskop.dao.RecenzijaDAO;
import com.bioskop.dao.FilmDAO;
import com.bioskop.dao.KorisnikDAO;
import com.bioskop.entity.Recenzija;
import com.bioskop.entity.Film;
import com.bioskop.entity.Korisnik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RecenzijaService {
    
    @Autowired
    private RecenzijaDAO recenzijaDAO;
    
    @Autowired
    private FilmDAO filmDAO;
    
    @Autowired
    private KorisnikDAO korisnikDAO;
    
    @Value("${bioskop.reviews.file.path:./reviews/}")
    private String reviewsFilePath;
    
    public List<Recenzija> getAllRecenzije() {
        return recenzijaDAO.findAll();
    }
    
    public Optional<Recenzija> getRecenzijaById(Long id) {
        return recenzijaDAO.findById(id);
    }
    
    public Recenzija saveRecenzija(Recenzija recenzija) {
        // Validate entities exist
        validateRecenzija(recenzija);
        
        // Check if user already reviewed this movie
        Optional<Film> film = filmDAO.findById(recenzija.getFilm().getId());
        Optional<Korisnik> korisnik = korisnikDAO.findById(recenzija.getKorisnik().getId());
        
        if (recenzijaDAO.existsByFilmAndKorisnik(film.get(), korisnik.get())) {
            throw new RuntimeException("Korisnik je već ostavio recenziju za ovaj film");
        }
        
        // Save to database
        Recenzija savedRecenzija = recenzijaDAO.save(recenzija);
        
        // Save to TXT file
        saveRecenzijaToFile(savedRecenzija);
        
        return savedRecenzija;
    }
    
    public Recenzija updateRecenzija(Long id, Recenzija updatedRecenzija) {
        Optional<Recenzija> existingRecenzija = recenzijaDAO.findById(id);
        if (existingRecenzija.isPresent()) {
            Recenzija recenzija = existingRecenzija.get();
            recenzija.setOcena(updatedRecenzija.getOcena());
            recenzija.setTekstRecenzije(updatedRecenzija.getTekstRecenzije());
            
            Recenzija savedRecenzija = recenzijaDAO.save(recenzija);
            
            // Update file
            saveRecenzijaToFile(savedRecenzija);
            
            return savedRecenzija;
        }
        throw new RuntimeException("Recenzija sa ID " + id + " nije pronađena");
    }
    
    public void deleteRecenzija(Long id) {
        if (!recenzijaDAO.existsById(id)) {
            throw new RuntimeException("Recenzija sa ID " + id + " nije pronađena");
        }
        recenzijaDAO.deleteById(id);
    }
    
    public List<Recenzija> getRecenzijeByFilm(Long filmId) {
        Optional<Film> film = filmDAO.findById(filmId);
        if (film.isEmpty()) {
            throw new RuntimeException("Film sa ID " + filmId + " nije pronađen");
        }
        return recenzijaDAO.findByFilmIdOrderByDatumDesc(filmId);
    }
    
    public List<Recenzija> getRecenzijeByKorisnik(Long korisnikId) {
        Optional<Korisnik> korisnik = korisnikDAO.findById(korisnikId);
        if (korisnik.isEmpty()) {
            throw new RuntimeException("Korisnik sa ID " + korisnikId + " nije pronađen");
        }
        return recenzijaDAO.findByKorisnik(korisnik.get());
    }
    
    public Double getAverageRatingForFilm(Long filmId) {
        return recenzijaDAO.findAverageRatingByFilmId(filmId);
    }
    
    private void validateRecenzija(Recenzija recenzija) {
        if (recenzija.getFilm() == null || recenzija.getFilm().getId() == null) {
            throw new RuntimeException("Film je obavezan");
        }
        if (recenzija.getKorisnik() == null || recenzija.getKorisnik().getId() == null) {
            throw new RuntimeException("Korisnik je obavezan");
        }
        
        // Validate entities exist
        if (!filmDAO.existsById(recenzija.getFilm().getId())) {
            throw new RuntimeException("Film sa ID " + recenzija.getFilm().getId() + " nije pronađen");
        }
        if (!korisnikDAO.existsById(recenzija.getKorisnik().getId())) {
            throw new RuntimeException("Korisnik sa ID " + recenzija.getKorisnik().getId() + " nije pronađen");
        }
    }
    
    private void saveRecenzijaToFile(Recenzija recenzija) {
        try {
            // Create reviews directory if it doesn't exist
            Path reviewsDir = Paths.get(reviewsFilePath);
            if (!Files.exists(reviewsDir)) {
                Files.createDirectories(reviewsDir);
            }
            
            // Create filename based on film ID
            String filename = "film_" + recenzija.getFilm().getId() + "_recenzije.txt";
            Path filePath = reviewsDir.resolve(filename);
            
            // Format review data
            String reviewData = String.format(
                "ID: %d%nFilm: %s%nKorisnik: %s%nOcena: %s%nDatum: %s%nRecenzija: %s%n%n",
                recenzija.getId(),
                recenzija.getFilm().getNaziv(),
                recenzija.getKorisnik().getPunoIme(),
                recenzija.getOcena().toString(),
                recenzija.getDatumRecenzije().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")),
                recenzija.getTekstRecenzije()
            );
            
            // Append to file
            Files.write(filePath, reviewData.getBytes(), 
                       StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            
        } catch (IOException e) {
            throw new RuntimeException("Greška pri čuvanju recenzije u fajl: " + e.getMessage());
        }
    }
}