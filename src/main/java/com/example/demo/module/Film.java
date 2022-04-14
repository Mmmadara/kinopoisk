package com.example.demo.module;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "film", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

}
