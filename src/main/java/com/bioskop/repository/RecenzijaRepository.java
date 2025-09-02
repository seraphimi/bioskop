package com.bioskop.repository;

import com.bioskop.entity.Recenzija;
import com.bioskop.entity.Film;
import com.bioskop.entity.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecenzijaRepository extends JpaRepository<Recenzija, Long> {
    
    List<Recenzija> findByFilm(Film film);
    
    List<Recenzija> findByKorisnik(Korisnik korisnik);
    
    @Query("SELECT r FROM Recenzija r WHERE r.film.id = :filmId ORDER BY r.datumRecenzije DESC")
    List<Recenzija> findByFilmIdOrderByDatumDesc(@Param("filmId") Long filmId);
    
    @Query("SELECT AVG(r.ocena) FROM Recenzija r WHERE r.film.id = :filmId")
    Double findAverageRatingByFilmId(@Param("filmId") Long filmId);
    
    boolean existsByFilmAndKorisnik(Film film, Korisnik korisnik);
}