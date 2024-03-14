package com.example.kinokylastus.repositories;

import com.example.kinokylastus.entity.Kinoseanss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface KinoseanssRepository extends JpaRepository<Kinoseanss, Long> {

    @Query("SELECT k FROM Kinoseanss k WHERE k.algusaeg > CURRENT_TIMESTAMP ")
    List<Kinoseanss> leiaNädalaSeansid();

    @Query("SELECT k FROM Kinoseanss k WHERE k.film.žanr = :genre AND k.algusaeg > :currentDate ORDER BY k.algusaeg ASC")
    Kinoseanss findFirstUpcomingByGenre(@Param("genre") String genre, @Param("currentDate") LocalDateTime currentDate);
}

