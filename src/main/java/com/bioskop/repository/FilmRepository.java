package com.bioskop.repository;

import com.bioskop.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    
    List<Film> findByZanrContainingIgnoreCase(String zanr);
    
    List<Film> findByNazivContainingIgnoreCase(String naziv);
    
    List<Film> findByReditelj(String reditelj);
    
    @Query("SELECT f FROM Film f WHERE f.ocena >= :minOcena")
    List<Film> findByOcenaGreaterThanEqual(@Param("minOcena") java.math.BigDecimal minOcena);
    
    @Query("SELECT f FROM Film f WHERE f.trajanje BETWEEN :minTrajanje AND :maxTrajanje")
    List<Film> findByTrajanjeRange(@Param("minTrajanje") Integer minTrajanje, @Param("maxTrajanje") Integer maxTrajanje);
}