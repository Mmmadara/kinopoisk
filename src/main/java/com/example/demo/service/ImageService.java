package com.example.demo.service;

import com.example.demo.entities.Film;
import com.example.demo.entities.ImageModel;
import com.example.demo.exceptions.FilmNotFoundException;
import com.example.demo.exceptions.ImageNotFoundException;
import com.example.demo.repository.FilmRepo;
import com.example.demo.repository.ImageRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class ImageService {

    public static final Logger LOG = LoggerFactory.getLogger(ImageService.class);
    private final ImageRepo imageRepo;
    private final FilmRepo filmRepo;

    @Autowired
    public ImageService(ImageRepo imageRepo, FilmRepo filmRepo) {
        this.imageRepo = imageRepo;
        this.filmRepo = filmRepo;
    }

    public ImageModel uploadImageToFilm(MultipartFile file, Long filmId) throws IOException{
        //добавить фильм exception
        Film film = filmRepo.findById(filmId)
                .orElseThrow(() -> new FilmNotFoundException("Cannot find film with id" + filmId));

        ImageModel imageModel = new ImageModel();
        imageModel.setFilmId(filmId);
        imageModel.setImageBytes(file.getBytes());
        imageModel.setImageBytes(compressBytes(file.getBytes()));
        imageModel.setName(film.getName());
        LOG.info("Uploading image to film {}",film.getName());

        return imageRepo.save(imageModel);
    }

    public ImageModel getImageToFilm(Long filmId){
        Film film = filmRepo.findById(filmId)
                .orElseThrow(() -> new FilmNotFoundException("Cannot find film with id " + filmId));
        ImageModel imageModel = imageRepo.findByFilmId(filmId)
                .orElseThrow(() -> new ImageNotFoundException("Cannot find image to Film " + film.getName()));

        if (!ObjectUtils.isEmpty(imageModel)){
            imageModel.setImageBytes(decompressBytes(imageModel.getImageBytes()));
        }

        return imageModel;
    }

    private byte[] compressBytes(byte[] data){
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()){
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        }catch (IOException e){
            LOG.error("Cannot compress Bytes");
        }
        System.out.println("Compressed Image Bytes Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    private static byte[] decompressBytes(byte[] data){
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()){
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0 , count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException e) {
            LOG.error("Cannot decompress Bytes");
        }
        return outputStream.toByteArray();
    }

    private <T>Collector<T, ?, T> toSingleFilmCollector(){
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1){
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }
        );
    }
}
