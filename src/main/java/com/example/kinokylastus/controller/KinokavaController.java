package com.example.kinokylastus.controller;
import com.example.kinokylastus.entity.Kinoseanss;
import com.example.kinokylastus.services.KinokavaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/kinokava")
public class KinokavaController {

    @Autowired
    private KinokavaService kinokavaService;
    /**
     *meetod, mis tagastab tulevasste kinoseansside listi
     */
    @GetMapping
    public List<Kinoseanss> getKinokava() {

        List<Kinoseanss> kinokava = kinokavaService.getKinokava();

        return kinokava;
    }
}
