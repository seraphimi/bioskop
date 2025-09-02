package com.bioskop.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "recenzije")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Recenzija {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Ocena je obavezna")
    @DecimalMin(value = "1.0", message = "Ocena mora biti između 1 i 10")
    @DecimalMax(value = "10.0", message = "Ocena mora biti između 1 i 10")
    @Column(nullable = false, precision = 3, scale = 1)
    private BigDecimal ocena;
    
    @NotBlank(message = "Tekst recenzije je obavezan")
    @Column(nullable = false, length = 2000)
    private String tekstRecenzije;
    
    @Column(nullable = false)
    private LocalDateTime datumRecenzije;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "korisnik_id", nullable = false)
    private Korisnik korisnik;
    
    // Constructors
    public Recenzija() {
        this.datumRecenzije = LocalDateTime.now();
    }
    
    public Recenzija(BigDecimal ocena, String tekstRecenzije, Film film, Korisnik korisnik) {
        this();
        this.ocena = ocena;
        this.tekstRecenzije = tekstRecenzije;
        this.film = film;
        this.korisnik = korisnik;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public BigDecimal getOcena() {
        return ocena;
    }
    
    public void setOcena(BigDecimal ocena) {
        this.ocena = ocena;
    }
    
    public String getTekstRecenzije() {
        return tekstRecenzije;
    }
    
    public void setTekstRecenzije(String tekstRecenzije) {
        this.tekstRecenzije = tekstRecenzije;
    }
    
    public LocalDateTime getDatumRecenzije() {
        return datumRecenzije;
    }
    
    public void setDatumRecenzije(LocalDateTime datumRecenzije) {
        this.datumRecenzije = datumRecenzije;
    }
    
    public Film getFilm() {
        return film;
    }
    
    public void setFilm(Film film) {
        this.film = film;
    }
    
    public Korisnik getKorisnik() {
        return korisnik;
    }
    
    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }
}