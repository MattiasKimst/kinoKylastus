package com.example.kinokylastus.entity;

import jakarta.persistence.*;



@Entity
@Table(name = "külastused")
public class Külastus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "külastaja_id")
    private Külastaja külastaja;

    private int istekoht;

    @ManyToOne
    @JoinColumn(name = "kinoseanss_id")
    private Kinoseanss kinoseanss;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Külastaja getKülastaja() {
        return külastaja;
    }

    public void setKülastaja(Külastaja külastaja) {
        this.külastaja = külastaja;
    }

    public int getIstekoht() {
        return istekoht;
    }

    public void setIstekoht(int istekoht) {
        this.istekoht = istekoht;
    }

    public Kinoseanss getKinoseanss() {
        return kinoseanss;
    }

    public void setKinoseanss(Kinoseanss kinoseanss) {
        this.kinoseanss = kinoseanss;
    }
}
