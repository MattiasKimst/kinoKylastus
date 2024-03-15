package com.example.kinokylastus.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "kinoseansid")
public class Kinoseanss {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    private LocalDateTime algusaeg;


    public Long getId() {
        return id;
    }

    public Kinoseanss() {
        // Default constructor
    }
    public Kinoseanss(Film film, LocalDateTime algusaeg) {
        this.film = film;
        this.algusaeg = algusaeg;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public LocalDateTime getAlgusaeg() {
        return algusaeg;
    }

    public void setAlgusaeg(LocalDateTime algusaeg) {
        this.algusaeg = algusaeg;
    }
}
