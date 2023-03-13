package ru.otus.spring.homework.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.homework.model.Book;
import ru.otus.spring.homework.model.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(CommentRepositoryJpa.class)
class CommentRepositoryJpaTest {
    @Autowired
    CommentRepositoryJpa commentRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @Test
    void findAllCommentsTest() {
        List<Comment> comments = commentRepositoryJpa.findAll();
        Assertions.assertEquals(4, comments.size());
        Assertions.assertEquals("1commentForBook1", comments.get(0).getComment());
        Assertions.assertEquals("2commentForBook1", comments.get(1).getComment());
    }

    @Test
    void findByIdIfDataExistsTest() {
        Optional<Comment> optionalActualComment = commentRepositoryJpa.findById(1);
        Comment expectedComment = em.find(Comment.class, 1);
        assertThat(optionalActualComment).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedComment);

    }

    @Test
    void findByIdIfDataNotExistsTest() {
        Optional<Comment> optionalActualComment = commentRepositoryJpa.findById(5);
        Assertions.assertFalse(optionalActualComment.isPresent());
    }

    @Test
    void saveCommentTest() {
        Comment savedComment = commentRepositoryJpa.saveOrUpdate(new Comment(1, "newComment"));
        Comment expectedComment = em.find(Comment.class, 5);
        Assertions.assertEquals(savedComment.getComment(), expectedComment.getComment());
        Assertions.assertEquals(savedComment.getBook_id(), expectedComment.getBook_id());
    }

    @Test
    void deleteByIdIfExistsTest() {
        int deletedRowsCount = commentRepositoryJpa.deleteById(1);
        Assertions.assertFalse(commentRepositoryJpa.findById(1).isPresent());
        Assertions.assertEquals(1, deletedRowsCount);
    }

    @Test
    void deleteByIdIfNotExistsTest() {
        int deletedRowsCount = commentRepositoryJpa.deleteById(5);
        Assertions.assertEquals(0, deletedRowsCount);
    }

    @Test
    void updateIfExistsTest() {
        commentRepositoryJpa.saveOrUpdate(new Comment(1, 2, "newComment"));
        Comment loadedComment = em.find(Comment.class, 1);
        Assertions.assertEquals("newComment", loadedComment.getComment());
        Assertions.assertEquals(2, loadedComment.getBook_id());
    }
}