package ru.otus.spring.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.homework.model.Genre;

import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, String> {
    Optional<Genre> findByGenreName(String genreName);
}
