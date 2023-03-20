package ru.otus.spring.homework.repository;

import ru.otus.spring.homework.model.Author;

import java.util.Optional;

public interface AuthorRepository {
    Optional<Author> findById(long id);
}
