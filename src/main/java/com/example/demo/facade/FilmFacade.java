package com.example.demo.facade;

import com.example.demo.dto.FilmDTO;
import com.example.demo.entities.Film;
import org.springframework.stereotype.Component;

@Component
public class FilmFacade {

    public FilmDTO filmToFilmDto(Film film){
        FilmDTO filmDTO = new FilmDTO();
        filmDTO.setName(film.getName());
        filmDTO.setId(film.getId());
        filmDTO.setActors(film.getActors());
        filmDTO.setDescription(film.getDescription());
        filmDTO.setGenre(film.getGenre());
        filmDTO.setPrizes(film.getPrizes());
        filmDTO.setProducer(filmDTO.getProducer());
        filmDTO.setScreenWriter(film.getScreenWriter());
        filmDTO.setAvg(filmDTO.getAvg());
        filmDTO.setNumOfScore(film.getNumOfScore());
        filmDTO.setLinkKinopoisk(film.getLinkKinopoisk());
        filmDTO.setUrlTrailer(filmDTO.getUrlTrailer());
        return filmDTO;
    }

}
