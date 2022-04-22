package com.example.demo.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String actors;
    private String producer;
    private String screenWriter;
    private String genre;
    private String prizes;
    private String urlTrailer;
    private String linkKinopoisk;
    private Long avg;
    private Long numOfScore;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "films", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @PrePersist
    protected void onCreate(){
        this.avg = 0L;
        this.numOfScore = 0L;
    }
}
