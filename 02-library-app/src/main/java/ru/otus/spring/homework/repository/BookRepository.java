package ru.otus.spring.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.homework.model.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
