package ru.otus.spring.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.homework.model.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
