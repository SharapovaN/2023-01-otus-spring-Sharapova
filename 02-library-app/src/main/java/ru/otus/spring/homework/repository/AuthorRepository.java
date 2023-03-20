package ru.otus.spring.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.homework.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
