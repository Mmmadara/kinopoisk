package com.example.demo.entities;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Entity
public class ImageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(columnDefinition = "BYTEA")
    private byte[] imageBytes;
    private Long filmId;
}
