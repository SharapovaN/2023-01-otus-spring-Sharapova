package ru.otus.spring.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.homework.model.Book;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {
}
