package com.example.kinokylastus.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "filmid")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pealkiri;
    private String žanr;
    private String keel;
    private int vanusepiirang;

    public Film( String pealkiri, String žanr, String keel, int vanusepiirang) {

        this.pealkiri = pealkiri;
        this.žanr = žanr;
        this.keel = keel;
        this.vanusepiirang = vanusepiirang;
    }

    public Film() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getŽanr() {
        return žanr;
    }


}

