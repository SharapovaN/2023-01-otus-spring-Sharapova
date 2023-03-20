package ru.otus.spring.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.homework.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
