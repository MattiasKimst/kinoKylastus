package com.example.kinokylastus.repositories;

import com.example.kinokylastus.entity.Kinoseanss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface KinoseanssRepository extends JpaRepository<Kinoseanss, Long> {

    @Query("SELECT k FROM Kinoseanss k WHERE k.algusaeg > CURRENT_TIMESTAMP ")
    List<Kinoseanss> leiaNädalaSeansid();
}
