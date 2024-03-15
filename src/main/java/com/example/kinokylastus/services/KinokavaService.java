package com.example.kinokylastus.services;
import com.example.kinokylastus.entity.Kinoseanss;
import com.example.kinokylastus.repositories.KinoseanssRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KinokavaService {

    @Autowired
    private KinoseanssRepository kinoseanssRepository;

    private static final Logger logger = LoggerFactory.getLogger(SoovitusService.class);

    public List<Kinoseanss> getKinokava() {
        // Päri andmebaasist kõik sobivad kinoseansid, st mille algusaeg on hilisem praegusest ajast
        List<Kinoseanss> kinokava = kinoseanssRepository.leiaTulevasedSeansid();

        return kinokava;
    }
}
