package com.example.demo.service;

import com.example.demo.dto.CommentDTO;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Film;
import com.example.demo.entities.User;
import com.example.demo.exceptions.FilmNotFoundException;
import com.example.demo.repository.CommentRepo;
import com.example.demo.repository.FilmRepo;
import com.example.demo.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {


    public static final Logger LOG = LoggerFactory.getLogger(CommentService.class);
    private final CommentRepo commentRepo;
    private final FilmRepo filmRepo;
    private final UserRepo userRepo;

    @Autowired
    public CommentService(CommentRepo commentRepo, FilmRepo filmRepo, UserRepo userRepo) {
        this.commentRepo = commentRepo;
        this.filmRepo = filmRepo;
        this.userRepo = userRepo;
    }

    public Comment saveComment(Long filmId, CommentDTO commentDTO, Principal principal){
        User user = getUserByPrinciple(principal);
        Film film = filmRepo.findById(filmId)
                .orElseThrow(() -> new FilmNotFoundException("Cannot find film with id" + filmId));

        Comment comment = new Comment();
        comment.setFilm(film);
        comment.setUserId(user.getId());
        comment.setUsername(user.getUsername());
        comment.setMessage(commentDTO.getMessage());

        LOG.info("Saving comment for Film {}", film.getName());

        return commentRepo.save(comment);
    }

    public List<Comment> getAllCommentsForFilm(Long filmId){
        Film film = filmRepo.findById(filmId)
                .orElseThrow(() -> new FilmNotFoundException("Cannot find film with id" + filmId));

        return commentRepo.findAllByFilm(film);
    }

    public void deleteComments(Long commentId){
        Optional<Comment> comment = commentRepo.findById(commentId);
        comment.ifPresent(commentRepo :: delete);
    }

    public User getUserByPrinciple(Principal principal){
        String username = principal.getName();
        return userRepo.findUserByUsername((username))
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }
}
