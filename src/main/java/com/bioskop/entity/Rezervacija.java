package com.bioskop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "rezervacije")
public class Rezervacija {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Broj odabranih sedišta je obavezan")
    @Min(value = 1, message = "Mora biti odabrano najmanje jedno sedište")
    @Column(nullable = false)
    private Integer odabranaSedista;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusRezervacije status;
    
    @NotNull(message = "Ukupna cena je obavezna")
    @DecimalMin(value = "0.0", message = "Ukupna cena mora biti veća od 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal ukupnaCena;
    
    @Column(nullable = false)
    private LocalDateTime datumRezervacije;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projekcija_id", nullable = false)
    private Projekcija projekcija;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "korisnik_id", nullable = false)
    private Korisnik korisnik;
    
    // Constructors
    public Rezervacija() {
        this.datumRezervacije = LocalDateTime.now();
        this.status = StatusRezervacije.REZERVISANO;
    }
    
    public Rezervacija(Integer odabranaSedista, BigDecimal ukupnaCena, Film film, Projekcija projekcija, Korisnik korisnik) {
        this();
        this.odabranaSedista = odabranaSedista;
        this.ukupnaCena = ukupnaCena;
        this.film = film;
        this.projekcija = projekcija;
        this.korisnik = korisnik;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getOdabranaSedista() {
        return odabranaSedista;
    }
    
    public void setOdabranaSedista(Integer odabranaSedista) {
        this.odabranaSedista = odabranaSedista;
    }
    
    public StatusRezervacije getStatus() {
        return status;
    }
    
    public void setStatus(StatusRezervacije status) {
        this.status = status;
    }
    
    public BigDecimal getUkupnaCena() {
        return ukupnaCena;
    }
    
    public void setUkupnaCena(BigDecimal ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }
    
    public LocalDateTime getDatumRezervacije() {
        return datumRezervacije;
    }
    
    public void setDatumRezervacije(LocalDateTime datumRezervacije) {
        this.datumRezervacije = datumRezervacije;
    }
    
    public Film getFilm() {
        return film;
    }
    
    public void setFilm(Film film) {
        this.film = film;
    }
    
    public Projekcija getProjekcija() {
        return projekcija;
    }
    
    public void setProjekcija(Projekcija projekcija) {
        this.projekcija = projekcija;
    }
    
    public Korisnik getKorisnik() {
        return korisnik;
    }
    
    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }
    
    public enum StatusRezervacije {
        REZERVISANO, KUPLJENO, OTKAZANO
    }
}