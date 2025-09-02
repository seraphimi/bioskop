package com.bioskop.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "filmovi")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Film {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Naziv filma je obavezan")
    @Column(nullable = false)
    private String naziv;
    
    @NotBlank(message = "Žanr je obavezan")
    @Column(nullable = false)
    private String zanr;
    
    @NotNull(message = "Trajanje je obavezno")
    @Min(value = 1, message = "Trajanje mora biti veće od 0")
    @Column(nullable = false)
    private Integer trajanje; // u minutama
    
    @NotBlank(message = "Reditelj je obavezan")
    @Column(nullable = false)
    private String reditelj;
    
    @Column(length = 1000)
    private String glumci;
    
    @DecimalMin(value = "0.0", message = "Ocena mora biti između 0 i 10")
    @DecimalMax(value = "10.0", message = "Ocena mora biti između 0 i 10")
    private BigDecimal ocena;
    
    @Column(length = 2000)
    private String opis;
    
    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Projekcija> projekcije;
    
    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rezervacija> rezervacije;
    
    // Constructors
    public Film() {}
    
    public Film(String naziv, String zanr, Integer trajanje, String reditelj, String glumci, BigDecimal ocena, String opis) {
        this.naziv = naziv;
        this.zanr = zanr;
        this.trajanje = trajanje;
        this.reditelj = reditelj;
        this.glumci = glumci;
        this.ocena = ocena;
        this.opis = opis;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNaziv() {
        return naziv;
    }
    
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    
    public String getZanr() {
        return zanr;
    }
    
    public void setZanr(String zanr) {
        this.zanr = zanr;
    }
    
    public Integer getTrajanje() {
        return trajanje;
    }
    
    public void setTrajanje(Integer trajanje) {
        this.trajanje = trajanje;
    }
    
    public String getReditelj() {
        return reditelj;
    }
    
    public void setReditelj(String reditelj) {
        this.reditelj = reditelj;
    }
    
    public String getGlumci() {
        return glumci;
    }
    
    public void setGlumci(String glumci) {
        this.glumci = glumci;
    }
    
    public BigDecimal getOcena() {
        return ocena;
    }
    
    public void setOcena(BigDecimal ocena) {
        this.ocena = ocena;
    }
    
    public String getOpis() {
        return opis;
    }
    
    public void setOpis(String opis) {
        this.opis = opis;
    }
    
    public List<Projekcija> getProjekcije() {
        return projekcije;
    }
    
    public void setProjekcije(List<Projekcija> projekcije) {
        this.projekcije = projekcije;
    }
    
    public List<Rezervacija> getRezervacije() {
        return rezervacije;
    }
    
    public void setRezervacije(List<Rezervacija> rezervacije) {
        this.rezervacije = rezervacije;
    }
}