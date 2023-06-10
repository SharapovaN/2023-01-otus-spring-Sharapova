package ru.otus.spring.homework.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.homework.model.entity.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
}
