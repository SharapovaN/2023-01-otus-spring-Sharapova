package ru.otus.spring.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.homework.model.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
