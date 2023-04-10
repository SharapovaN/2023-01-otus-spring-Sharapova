package ru.otus.spring.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.homework.model.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findAllByBookId(String bookId);
}
