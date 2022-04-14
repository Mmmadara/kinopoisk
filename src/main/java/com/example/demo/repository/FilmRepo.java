package com.example.demo.repository;

import com.example.demo.module.Film;
import com.example.demo.module.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepo extends JpaRepository<Film, Long> {

    List<Film> findAllByUserOrderByCreatedDateDesc(User user);
    List<Film> findAllByOrderByCreatedDateDesc();
    Optional<Film> findPostByIdaAndUser(Long id, User user);
}
