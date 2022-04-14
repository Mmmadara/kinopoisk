package com.example.demo.repository;

import com.example.demo.module.Comment;
import com.example.demo.module.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

    List<Comment> findAllByFilm(Film film);
    Comment findByIdAndUserId(Long commentId, Long userId);
}
