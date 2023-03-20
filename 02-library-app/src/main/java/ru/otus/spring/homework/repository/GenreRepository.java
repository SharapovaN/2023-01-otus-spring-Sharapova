package ru.otus.spring.homework.repository;

import ru.otus.spring.homework.model.Genre;

import java.util.Optional;

public interface GenreRepository {
    Optional<Genre> findById(long id);
}
