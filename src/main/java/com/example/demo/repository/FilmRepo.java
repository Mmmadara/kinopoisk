package com.example.demo.repository;

import com.example.demo.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepo extends JpaRepository<Film, Long> {
    List<Film> findAllByGenre(String genre);
    List<Film> findAllByActorsLike(String actor);
    List<Film> findAllByNameLike(String name);
}
