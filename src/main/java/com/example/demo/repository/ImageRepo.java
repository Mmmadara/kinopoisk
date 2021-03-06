package com.example.demo.repository;

import com.example.demo.entities.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepo extends JpaRepository<ImageModel, Long> {

    Optional<ImageModel> findByFilmId(Long filmId);
    Optional<ImageModel> findById(Long id);

}
