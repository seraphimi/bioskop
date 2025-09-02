package com.bioskop.repository;

import com.bioskop.entity.Projekcija;
import com.bioskop.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProjekcijaRepository extends JpaRepository<Projekcija, Long> {
    
    List<Projekcija> findByFilm(Film film);
    
    List<Projekcija> findBySala(Integer sala);
    
    @Query("SELECT p FROM Projekcija p WHERE p.datumVreme >= :startDate AND p.datumVreme <= :endDate")
    List<Projekcija> findByDatumVremeRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT p FROM Projekcija p WHERE p.dostupnaSedista > 0")
    List<Projekcija> findAvailableProjections();
    
    @Query("SELECT p FROM Projekcija p WHERE p.film.id = :filmId AND p.datumVreme >= :currentTime ORDER BY p.datumVreme")
    List<Projekcija> findUpcomingProjectionsByFilm(@Param("filmId") Long filmId, @Param("currentTime") LocalDateTime currentTime);
    
    @Query("SELECT p FROM Projekcija p WHERE p.datumVreme >= :currentTime ORDER BY p.datumVreme")
    List<Projekcija> findUpcomingProjections(@Param("currentTime") LocalDateTime currentTime);
}