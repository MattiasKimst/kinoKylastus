package com.example.kinokylastus.services;

import com.example.kinokylastus.entity.Kinoseanss;
import com.example.kinokylastus.entity.Kylastaja;
import com.example.kinokylastus.entity.Kylastus;
import com.example.kinokylastus.repositories.KinoseanssRepository;
import com.example.kinokylastus.repositories.KylastajaRepository;
import com.example.kinokylastus.repositories.KylastusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SoovitusService {
    private static final Logger logger = LoggerFactory.getLogger(SoovitusService.class);

    @Autowired
    private KylastajaRepository kylastajaRepository;

    @Autowired
    private KylastusRepository kylastusRepository;

    @Autowired
    private KinoseanssRepository kinoseanssRepository;

    /**
     * meetos, mis leiab id põhjal andmebaasist külastaja
     * @param id külastajale vastav ID
     * @return külastaja
     */
    public Kylastaja leiaKylastaja(Long id) {
        return kylastajaRepository.findById(id).orElse(null);
    }

    /**
     * meetod, mis leiab andmebaasist kõik filmid-seansid, mida etteantud külastaja on vaadanud
     * @param kylastaja
     * @return List<Kylastus> kõik külastused, mida kylastaja on teinud
     */
    public List<Kylastus> leiaKõikKylastajaKylastused(Kylastaja kylastaja) {
        return kylastusRepository.findByKylastaja(kylastaja);
    }

    /**
     * meetod, mis etteantud külastaja id põhjal leiab abimeetodiga külastaja andmebaasist, leiab selle külastaja kõik
     * külastused abimeetodiga andmebaasist, ja leiab žanri, mida külastaja on enim vaadanud.
     * @param id
     * @return
     */
    public Kinoseanss soovitaSeanssi(Long id) {
        //leiame külastaja
        Kylastaja kylastaja = leiaKylastaja(id);
        logger.info("leitud kylastaja: "+kylastaja.getNimi());

        // leiame kõik külastaja külastused
        List<Kylastus> kylastused = leiaKõikKylastajaKylastused(kylastaja);
        logger.info("leitud kylastused: "+kylastused);

        // arvutame kõige külastatuma žanri
        String külastatuimŽanr = arvutaKülastatuimZanr(kylastused);
        logger.info(külastatuimŽanr);

        // võtame kõige varem toimuva kõige külastatuma žanri filmi
        return kinoseanssRepository.findFirstUpcomingByGenre(külastatuimŽanr, LocalDateTime.now());
    }

    /**
     * meetod, mis leiab külastaja külastuste seast vaadatuima žanri
     * @param kylastused
     * @return külastatuima žanri nimi sõnena
     */
    private String arvutaKülastatuimZanr(List<Kylastus> kylastused) {
        // hashmap külastusajaloos vaadatud žanrite kokkulugemiseks
        Map<String, Integer> žanriVaatamised = new HashMap<>();

        // Käime kõik külastaja žanrid läbi ja suurendame vaadeldava filmi zanrile vastava külastuste arvu loendurit,
        for (Kylastus kylastus : kylastused) {
            String žanr = kylastus.getKinoseanss().getFilm().getŽanr();
            logger.info("Suurendan mapis žanri "+žanr+" vaatamiste arvu");
            žanriVaatamised.put(žanr, žanriVaatamised.getOrDefault(žanr, 0) + 1);
        }

        // leiame vaadatuima žanri vaadates mapi läbi ja jättes meelde suurima külastamiste arvuga žanri
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
