package ru.otus.spring.homework.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.homework.model.Genre;

import java.util.Optional;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
    Optional<Genre> findByGenreName(String genreName);
}
