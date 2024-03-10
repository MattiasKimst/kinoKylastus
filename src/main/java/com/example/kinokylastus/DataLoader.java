package com.example.kinokylastus;

import com.example.kinokylastus.entity.Film;
import com.example.kinokylastus.entity.Kinoseanss;
import com.example.kinokylastus.entity.Kylastaja;
import com.example.kinokylastus.entity.Kylastus;
import com.example.kinokylastus.repositories.FilmRepository;
import com.example.kinokylastus.repositories.KinoseanssRepository;
import com.example.kinokylastus.repositories.KylastajaRepository;
import com.example.kinokylastus.repositories.KylastusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements ApplicationRunner {

    private final FilmRepository filmRepository;
    private final KinoseanssRepository kinoseanssRepository;
    private final KylastajaRepository kylastajaRepository;
    private final KylastusRepository kylastusRepository;

    @Autowired
    public DataLoader(FilmRepository filmRepository, KinoseanssRepository kinoseanssRepository,
                      KylastajaRepository kylastajaRepository, KylastusRepository kylastusRepository) {
        this.filmRepository = filmRepository;
        this.kinoseanssRepository = kinoseanssRepository;
        this.kylastajaRepository = kylastajaRepository;
        this.kylastusRepository = kylastusRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        populateSampleData();
    }

    private void populateSampleData() {
        // näidiskirjed filmide tabelisse
        Film film1 = new Film("Film 1", "Genre 1", "Language 1", 18);
        Film film2 = new Film("Film 2", "Genre 2", "Language 2", 12);

        filmRepository.save(film1);
        filmRepository.save(film2);

        // näidiskirjed kinoseansside tabelisse
        Kinoseanss kinoseanss1 = new Kinoseanss(film1, LocalDateTime.now().plusDays(1));
        Kinoseanss kinoseanss2 = new Kinoseanss(film2, LocalDateTime.now().plusDays(2));

        kinoseanssRepository.save(kinoseanss1);
        kinoseanssRepository.save(kinoseanss2);

        // Näidiskirjed külastajad tabelisse
        Kylastaja kylastaja1 = new Kylastaja("Visitor 1", 25);
        Kylastaja kylastaja2 = new Kylastaja("Visitor 2", 30);

        kylastajaRepository.save(kylastaja1);
        kylastajaRepository.save(kylastaja2);

        // näidis sisu kylastus tabelisse
        Kylastus kylastus1 = new Kylastus(kylastaja1, 1, kinoseanss1);
        Kylastus kylastus2 = new Kylastus(kylastaja2, 2, kinoseanss2);

        kylastusRepository.save(kylastus1);
        kylastusRepository.save(kylastus2);
    }
}
