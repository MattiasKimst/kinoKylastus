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

    public Kylastaja getKylastaja() {
        return kylastaja;
    }

    public void setKylastaja(Kylastaja kylastaja) {
        this.kylastaja = kylastaja;
    }

    public int getIstekoht() {
        return istekoht;
    }

    public void setIstekoht(int istekoht) {
        this.istekoht = istekoht;
    }

    public void setKinoseanss(Kinoseanss kinoseanss) {
        this.kinoseanss = kinoseanss;
    }
}
