package com.example.kinokylastus.services;
import com.example.kinokylastus.entity.Kinoseanss;
import com.example.kinokylastus.repositories.KinoseanssRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class KinokavaService {

    @Autowired
    private KinoseanssRepository kinoseanssRepository;


    public List<Kinoseanss> getKinokava() {
        // Päri andmebaasist kõik sobivad kinoseansid
        List<Kinoseanss> kinokava = kinoseanssRepository.leiaNädalaSeansid();

        return kinokava;
    }
}
