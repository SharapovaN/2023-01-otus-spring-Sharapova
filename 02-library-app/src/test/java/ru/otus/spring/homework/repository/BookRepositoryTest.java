package ru.otus.spring.homework.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.homework.event.MongoBookCascadeEventsListener;
import ru.otus.spring.homework.event.MongoCommentCascadeEventsListener;
import ru.otus.spring.homework.model.Book;
import ru.otus.spring.homework.model.Comment;

import java.util.Set;

@Import({MongoBookCascadeEventsListener.class, MongoCommentCascadeEventsListener.class})
@DataMongoTest(properties = {"spring.mongodb.embedded.version=4.0.2"})
@ExtendWith(SpringExtension.class)
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    public void removeCommentArrayElementByIdTest() {
        Book bookWithTwoComments = bookRepository.findById("642414e251c1e2380fb49ab8").get();
        Assertions.assertEquals(2, bookWithTwoComments.getComments().size());

        bookRepository.removeCommentArrayElementById("642414e251c1e2380fb49abe");

        Book bookWithOneComment = bookRepository.findById("642414e251c1e2380fb49ab8").get();
        Assertions.assertEquals(1, bookWithOneComment.getComments().size());
    }

    @Test
    public void getBookCommentsByIdTest() {
        Set<Comment> comments = bookRepository.getBookCommentsById("642414e251c1e2380fb49ab8");

        Assertions.assertEquals(2, comments.size());
    }
}