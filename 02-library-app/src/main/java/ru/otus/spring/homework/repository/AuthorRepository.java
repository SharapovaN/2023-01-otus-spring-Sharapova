package ru.otus.spring.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.homework.model.Author;

@Transactional(propagation = Propagation.SUPPORTS)
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
