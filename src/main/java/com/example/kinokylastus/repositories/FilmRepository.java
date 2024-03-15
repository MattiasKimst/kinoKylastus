package com.example.kinokylastus.repositories;
import com.example.kinokylastus.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Long> {

}
