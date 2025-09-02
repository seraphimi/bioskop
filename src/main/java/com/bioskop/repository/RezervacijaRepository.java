package com.bioskop.repository;

import com.bioskop.entity.Rezervacija;
import com.bioskop.entity.Korisnik;
import com.bioskop.entity.Film;
import com.bioskop.entity.Projekcija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RezervacijaRepository extends JpaRepository<Rezervacija, Long> {
    
    List<Rezervacija> findByKorisnik(Korisnik korisnik);
    
    List<Rezervacija> findByFilm(Film film);
    
    List<Rezervacija> findByProjekcija(Projekcija projekcija);
    
    List<Rezervacija> findByStatus(Rezervacija.StatusRezervacije status);
    
    @Query("SELECT r FROM Rezervacija r WHERE r.korisnik.id = :korisnikId ORDER BY r.datumRezervacije DESC")
    List<Rezervacija> findByKorisnikIdOrderByDatumDesc(@Param("korisnikId") Long korisnikId);
    
    @Query("SELECT COUNT(r) FROM Rezervacija r WHERE r.projekcija.id = :projekcijaId")
    Long countByProjekcijaId(@Param("projekcijaId") Long projekcijaId);
}