package com.example.kinokylastus.controller;


import com.example.kinokylastus.entity.Kinoseanss;
import com.example.kinokylastus.services.SoovitusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class SoovitusedController {

    private final SoovitusService soovitusService;

    @Autowired
    public SoovitusedController(SoovitusService soovitusedService) {
        this.soovitusService = soovitusedService;
    }

    /**
     * meetod, mis tagastan etteantud külastaja ID põhjal soovituse
     * @param kylastajaId külastajale vastav id
     * @return soovitatud Kinoseanss
     */
    @GetMapping("/soovitused/{kylastajaId}")
    public Kinoseanss getRecommendedKinoseanss(@PathVariable Long kylastajaId) {
        return soovitusService.soovitaSeanssi(kylastajaId);
    }
}
