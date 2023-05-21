package ru.otus.spring.homework.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.homework.model.Comment;

import java.util.List;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
    List<Comment> findAllByBookId(String bookId);
}
