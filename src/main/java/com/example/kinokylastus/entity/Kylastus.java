package com.example.kinokylastus.entity;

import jakarta.persistence.*;



@Entity
@Table(name = "kylastused")
public class Kylastus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "külastaja_id")
    private Kylastaja kylastaja;

    private int istekoht;

    @ManyToOne
    @JoinColumn(name = "kinoseanss_id")
    private Kinoseanss kinoseanss;


    public Kylastus(Kylastaja kylastaja, int istekoht, Kinoseanss kinoseanss) {
        this.kylastaja = kylastaja;
        this.istekoht = istekoht;
        this.kinoseanss = kinoseanss;
    }

    public Kylastus() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Kinoseanss getKinoseanss() {
        return kinoseanss;
    }


}
