package com.bioskop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Future;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "projekcije")
public class Projekcija {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Datum i vreme su obavezni")
    @Future(message = "Datum i vreme projekcije moraju biti u budućnosti")
    @Column(nullable = false)
    private LocalDateTime datumVreme;
    
    @NotNull(message = "Sala je obavezna")
    @Min(value = 1, message = "Broj sale mora biti veći od 0")
    @Column(nullable = false)
    private Integer sala;
    
    @NotNull(message = "Dostupna sedišta su obavezna")
    @Min(value = 0, message = "Broj dostupnih sedišta ne može biti negativan")
    @Column(nullable = false)
    private Integer dostupnaSedista;
    
    @NotNull(message = "Ukupna sedišta su obavezna")
    @Min(value = 1, message = "Ukupan broj sedišta mora biti veći od 0")
    @Column(nullable = false)
    private Integer ukupnaSedista;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;
    
    @OneToMany(mappedBy = "projekcija", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rezervacija> rezervacije;
    
    // Constructors
    public Projekcija() {}
    
    public Projekcija(LocalDateTime datumVreme, Integer sala, Integer ukupnaSedista, Film film) {
        this.datumVreme = datumVreme;
        this.sala = sala;
        this.ukupnaSedista = ukupnaSedista;
        this.dostupnaSedista = ukupnaSedista; // Initially all seats are available
        this.film = film;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDateTime getDatumVreme() {
        return datumVreme;
    }
    
    public void setDatumVreme(LocalDateTime datumVreme) {
        this.datumVreme = datumVreme;
    }
    
    public Integer getSala() {
        return sala;
    }
    
    public void setSala(Integer sala) {
        this.sala = sala;
    }
    
    public Integer getDostupnaSedista() {
        return dostupnaSedista;
    }
    
    public void setDostupnaSedista(Integer dostupnaSedista) {
        this.dostupnaSedista = dostupnaSedista;
    }
    
    public Integer getUkupnaSedista() {
        return ukupnaSedista;
    }
    
    public void setUkupnaSedista(Integer ukupnaSedista) {
        this.ukupnaSedista = ukupnaSedista;
    }
    
    public Film getFilm() {
        return film;
    }
    
    public void setFilm(Film film) {
        this.film = film;
    }
    
    public List<Rezervacija> getRezervacije() {
        return rezervacije;
    }
    
    public void setRezervacije(List<Rezervacija> rezervacije) {
        this.rezervacije = rezervacije;
    }
}