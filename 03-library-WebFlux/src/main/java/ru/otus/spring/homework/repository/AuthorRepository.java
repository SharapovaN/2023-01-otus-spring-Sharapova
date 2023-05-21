package ru.otus.spring.homework.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.homework.model.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
}
