package ru.otus.spring.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.homework.model.Author;

import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {
    Optional<Author> findByAuthorName(String authorName);
}
