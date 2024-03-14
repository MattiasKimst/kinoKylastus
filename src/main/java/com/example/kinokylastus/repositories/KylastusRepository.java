package com.example.kinokylastus.repositories;
import com.example.kinokylastus.entity.Kylastaja;
import com.example.kinokylastus.entity.Kylastus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KylastusRepository extends JpaRepository<Kylastus, Long> {
    List<Kylastus> findByKylastaja(Kylastaja kylastaja);
}
