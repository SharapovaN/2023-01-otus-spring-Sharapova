package ru.otus.spring.homework.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.homework.model.entity.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String>, BookRepositoryCustom {
}
