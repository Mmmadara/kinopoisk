package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class FilmDTO {
    @NotEmpty
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotEmpty
    private String actors;
    @NotEmpty
    private String producer;
    @NotEmpty
    private String screenWriter;
    @NotEmpty
    private String genre;
    @NotEmpty
    private String prizes;
    private String urlTrailer;
    private String linkKinopoisk;
    private Long avg;
    private Long numOfScore;
}
