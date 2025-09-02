package com.bioskop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

import java.util.List;

@Entity
@Table(name = "korisnici")
public class Korisnik {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Ime je obavezno")
    @Column(nullable = false)
    private String ime;
    
    @NotBlank(message = "Prezime je obavezno")
    @Column(nullable = false)
    private String prezime;
    
    @Email(message = "E-mail mora biti valjan")
    @NotBlank(message = "E-mail je obavezan")
    @Column(nullable = false, unique = true)
    private String email;
    
    @OneToMany(mappedBy = "korisnik", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rezervacija> rezervacije;
    
    // Constructors
    public Korisnik() {}
    
    public Korisnik(String ime, String prezime, String email) {
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getIme() {
        return ime;
    }
    
    public void setIme(String ime) {
        this.ime = ime;
    }
    
    public String getPrezime() {
        return prezime;
    }
    
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public List<Rezervacija> getRezervacije() {
        return rezervacije;
    }
    
    public void setRezervacije(List<Rezervacija> rezervacije) {
        this.rezervacije = rezervacije;
    }
    
    public String getPunoIme() {
        return ime + " " + prezime;
    }
}