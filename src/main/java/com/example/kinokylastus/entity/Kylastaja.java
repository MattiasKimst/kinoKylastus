package com.example.kinokylastus.entity;

import jakarta.persistence.*;



@Entity
@Table(name = "kylastajad")
public class Kylastaja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nimi;
    private int vanus;

    public Kylastaja(String nimi, int vanus) {
        this.nimi = nimi;
        this.vanus = vanus;
    }

    public Kylastaja() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public int getVanus() {
        return vanus;
    }

    public void setVanus(int vanus) {
        this.vanus = vanus;
    }

    // Constructor, getterid ja setterid
}

