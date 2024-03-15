package com.example.kinokylastus.repositories;
import com.example.kinokylastus.entity.Film;
import com.example.kinokylastus.entity.Kylastaja;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KylastajaRepository extends JpaRepository<Kylastaja, Long> {

}
