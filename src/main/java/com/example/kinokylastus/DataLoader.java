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

    /**
     * Meetod, mis loob näidisandemed andmebaasi, et oleks millega testida
     */
    private void populateSampleData() {
        // näidiskirjed filmide tabelisse
        Film film1 = new Film("Film 1", "Draama", "Eesti", 18);
        Film film2 = new Film("Film 2", "Draama", "Inglise", 12);
        Film film3 = new Film("Film 3", "Komöödia", "Inglise", 11);
        Film film4 = new Film("Film 4", "Komöödia", "Inglise", 15);
        Film film5 = new Film("Film 5", "Komöödia", "Inglise", 10);

        filmRepository.save(film1);
        filmRepository.save(film2);
        filmRepository.save(film3);
        filmRepository.save(film4);
        filmRepository.save(film5);

        // näidiskirjed kinoseansside tabelisse
        Kinoseanss kinoseanss1 = new Kinoseanss(film2, LocalDateTime.now().plusDays(1));
        Kinoseanss kinoseanss2 = new Kinoseanss(film1, LocalDateTime.now().plusDays(7));
        Kinoseanss kinoseanss3 = new Kinoseanss(film3, LocalDateTime.now().plusDays(3));
        Kinoseanss kinoseanss4 = new Kinoseanss(film4, LocalDateTime.now().plusDays(4));
        Kinoseanss kinoseanss5 = new Kinoseanss(film5, LocalDateTime.now().plusDays(5));
        //järgnevalt minevikus olnud kinoseansid- neid ei peaks tabelis kuvama, alusel soovituste tegemisel
        Kinoseanss kinoseanss6 = new Kinoseanss(film1, LocalDateTime.now().minusDays(7));
        Kinoseanss kinoseanss7 = new Kinoseanss(film4, LocalDateTime.now().minusDays(14));
        Kinoseanss kinoseanss8 = new Kinoseanss(film3, LocalDateTime.now().minusDays(10));

        kinoseanssRepository.save(kinoseanss1);
        kinoseanssRepository.save(kinoseanss2);
        kinoseanssRepository.save(kinoseanss3);
        kinoseanssRepository.save(kinoseanss4);
        kinoseanssRepository.save(kinoseanss5);
        kinoseanssRepository.save(kinoseanss6);
        kinoseanssRepository.save(kinoseanss7);
        kinoseanssRepository.save(kinoseanss8);

        // Näidiskirjed külastajad tabelisse
        Kylastaja kylastaja1 = new Kylastaja("Külastaja 1", 25);
        Kylastaja kylastaja2 = new Kylastaja("Külastaja 2", 30);

        kylastajaRepository.save(kylastaja1);
        kylastajaRepository.save(kylastaja2);

        // näidis sisu kylastus tabelisse
        //kylastaja 1 on varem vaadanud ainult draamat, kylastaja2 aga üks kord draamat ja kaks korda komöödiat
        Kylastus kylastus1 = new Kylastus(kylastaja1, 1, kinoseanss6);
        Kylastus kylastus2 = new Kylastus(kylastaja2, 2, kinoseanss6);
        Kylastus kylastus3 = new Kylastus(kylastaja2, 2, kinoseanss7);
        Kylastus kylastus4 = new Kylastus(kylastaja2, 2, kinoseanss8);


        kylastusRepository.save(kylastus1);
        kylastusRepository.save(kylastus2);
        kylastusRepository.save(kylastus3);
        kylastusRepository.save(kylastus4);
    }
}
