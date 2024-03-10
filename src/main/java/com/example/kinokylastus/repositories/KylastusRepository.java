package com.example.kinokylastus.repositories;
import com.example.kinokylastus.entity.Film;
import com.example.kinokylastus.entity.Kylastus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KylastusRepository extends JpaRepository<Kylastus, Long> {
    // Additional custom methods can be defined here if needed
}
