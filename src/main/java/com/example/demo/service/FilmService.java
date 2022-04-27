package com.example.demo.service;

import com.example.demo.dto.FilmDTO;
import com.example.demo.entities.Film;
import com.example.demo.exceptions.FilmNotFoundException;
import com.example.demo.repository.FilmRepo;
import com.example.demo.repository.ImageRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {

    public static final Logger LOG = LoggerFactory.getLogger(FilmService.class);

    private final FilmRepo filmRepo;
    private final ImageRepo imageRepo;


    public FilmService(FilmRepo filmRepo, ImageRepo imageRepo) {
        this.filmRepo = filmRepo;
        this.imageRepo = imageRepo;
    }

    public Film addFilm(FilmDTO filmDTO){
        Film film = new Film();
        film.setName(filmDTO.getName());
        film.setActors(filmDTO.getActors());
        film.setLinkKinopoisk(filmDTO.getLinkKinopoisk());
        film.setDescription(filmDTO.getDescription());
        film.setGenre(filmDTO.getGenre());
        film.setPrizes(filmDTO.getPrizes());
        film.setProducer(filmDTO.getProducer());
        film.setScreenWriter(filmDTO.getScreenWriter());
        film.setUrlTrailer(filmDTO.getUrlTrailer());

        LOG.info("Saving new Film {}", filmDTO.getName());

        return filmRepo.save(film);
    }

    public Film scoreFilm(Long filmId, Long score){
        Film film = filmRepo.getOne(filmId);
        film.setAvg((film.getAvg() * film.getNumOfScore() + score)/film.getNumOfScore());

        return filmRepo.save(film);

    }

    public Film getFilmById(Long filmId){
        return filmRepo.findById(filmId).orElseThrow(() -> new FilmNotFoundException("Cannot find film with id " + filmId));
    }

    public List<Film> getAllFilms(){
        return filmRepo.findAll();
    }

    public List<Film> getSortedFilmsByGenre(String genre){
        return filmRepo.findAllByGenre(genre);
    }
    public List<Film> getSortedFilmsByActors(String actors){
        return filmRepo.findAllByActorsLike(actors);
    }
    public List<Film> getSortedFilmsByName(String name){
        return filmRepo.findAllByNameLike(name);
    }

}
