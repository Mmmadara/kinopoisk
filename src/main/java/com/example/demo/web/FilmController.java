package com.example.demo.web;

import com.example.demo.dto.FilmDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entities.Film;
import com.example.demo.facade.FilmFacade;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.service.FilmService;
import com.example.demo.validations.ResponseErrorValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/film")
@CrossOrigin
public class FilmController {
    private FilmFacade filmFacade;
    private FilmService filmService;
    private ResponseErrorValidation responseErrorValidation;

    public FilmController(FilmFacade filmFacade, FilmService filmService, ResponseErrorValidation responseErrorValidation) {
        this.filmFacade = filmFacade;
        this.filmService = filmService;
        this.responseErrorValidation = responseErrorValidation;
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addFilm(@Valid @RequestBody FilmDTO filmDTO, BindingResult bindingResult, Principal principal){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (ObjectUtils.isEmpty(errors)){
            return errors;
        }

        Film film = filmService.addFilm(filmDTO);
        FilmDTO addedFiilm = filmFacade.filmToFilmDto(film);

        return new ResponseEntity<>(addedFiilm, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FilmDTO>> getAllFilms(){
        List<FilmDTO> filmDTOList = filmService.getAllFilms()
                .stream()
                .map(filmFacade::filmToFilmDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(filmDTOList, HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> scoreFilm(@PathVariable("filmId, score") String filmId, String score){
        filmService.scoreFilm(Long.parseLong(filmId), Long.parseLong(score));
        return new ResponseEntity<>(new MessageResponse("You scored this film"), HttpStatus.OK);
    }


    public ResponseEntity<MessageResponse> deleteFilm(@PathVariable("filmId") String filmId){
        filmService.deleteFilm(Long.parseLong(filmId));
        return new ResponseEntity<>(new MessageResponse("Film was deleted"), HttpStatus.OK);
    }


}
