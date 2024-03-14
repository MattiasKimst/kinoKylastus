package com.example.kinokylastus.services;

import com.example.kinokylastus.entity.Kinoseanss;
import com.example.kinokylastus.entity.Kylastaja;
import com.example.kinokylastus.entity.Kylastus;
import com.example.kinokylastus.repositories.KinoseanssRepository;
import com.example.kinokylastus.repositories.KylastajaRepository;
import com.example.kinokylastus.repositories.KylastusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SoovitusService {

    @Autowired
    private KylastajaRepository kylastajaRepository;

    @Autowired
    private KylastusRepository kylastusRepository;

    @Autowired
    private KinoseanssRepository kinoseanssRepository;

    public Kylastaja leiaKylastaja(Long id) {
        return kylastajaRepository.findById(id).orElse(null);
    }

    public List<Kylastus> leiaKõikKylastajaKylastused(Kylastaja kylastaja) {
        return kylastusRepository.findByKylastaja(kylastaja);
    }

    public Kinoseanss soovitaSeanssi(Long id) {
        Kylastaja kylastaja = leiaKylastaja(id);
        // leiame kõik külastaja külastused
        List<Kylastus> kylastused = leiaKõikKylastajaKylastused(kylastaja);

        // arvutame kõige külastatuma žanri
        String mostVisitedGenre = arvutaKülastatuimZanr(kylastused);

        // võtame esimese ettetuleva kõige külastatuma žanri filmi
        return kinoseanssRepository.findFirstUpcomingByGenre(mostVisitedGenre, LocalDateTime.now());
    }

    private String arvutaKülastatuimZanr(List<Kylastus> kylastused) {
        // hashmap külastusajaloos vaadatud žanrite kokkulugemiseks
        Map<String, Integer> žanriVaatamised = new HashMap<>();

        // Käime kõik külastaja žanrid läbi ja suurendame vaadeldava filmi zanrile vastava külastuste arvu loendurit,
        for (Kylastus kylastus : kylastused) {
            String žanr = kylastus.getKinoseanss().getFilm().getŽanr();
            žanriVaatamised.put(žanr, žanriVaatamised.getOrDefault(žanr, 0) + 1);
        }

        // leiame vaadatuima žanri
        String külastatuim = null;
        int suurim = 0;
        for (Map.Entry<String, Integer> entry : žanriVaatamised.entrySet()) {
            if (entry.getValue() > suurim) {
                külastatuim = entry.getKey();
                suurim = entry.getValue();
            }
        }

        return külastatuim;
    }

}
