package ru.otus.spring.homework.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.spring.homework.model.entity.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
    Flux<Comment> findAllByBookId(String bookId);
}
