package ru.otus.spring.homework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.homework.model.entity.Comment;
import ru.otus.spring.homework.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    BookServiceImpl bookService;

    @Mock
    private CommentRepository commentRepository;

    @Test
    void getAllCommentsTest() {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1, 1, "comment1"));
        comments.add(new Comment(2, 1, "comment2"));
        given(commentRepository.findAll()).willReturn(comments);
        List<String> commentList = commentService.getAll();
        Assertions.assertEquals(2, commentList.size());
    }

    @Test
    void getByIdIfExistsTest() {
        given(commentRepository.findById(1L)).willReturn(Optional.of(new Comment(1, 1, "comment1")));
        String comment = commentService.getById(1);
        Assertions.assertTrue(comment.contains("comment1"));
    }

    @Test
    void getByIdIfNotExistsTest() {
        given(commentRepository.findById(1L)).willReturn(Optional.empty());
        String comment = commentService.getById(1);
        Assertions.assertTrue(comment.contains("not found"));
    }

    @Test
    void createIfOkTest() {
        given(commentRepository.save(new Comment(1, "comment1")))
                .willReturn(new Comment(1, "comment1"));
        given(bookService.checkBookExists(1)).willReturn(true);
        String comment = commentService.create(1, "comment1");
        Assertions.assertTrue(comment.contains("Comment successfully created"));
    }

    @Test
    void createIfBookNotExistsTest() {
        given(bookService.checkBookExists(3)).willReturn(false);
        String comment = commentService.create(3, "comment1");
        Assertions.assertTrue(comment.contains("Comment creation failed"));
    }

    @Test
    void deleteByIdIfCommentExistTest() {
        given(commentRepository.findById(1L)).willReturn(Optional.of(new Comment(1)));
        String comment = commentService.deleteById(1);
        Assertions.assertTrue(comment.contains("Comment successfully deleted"));
    }

    @Test
    void deleteByIdIfCommentNotExistTest() {
        given(commentRepository.findById(1L)).willReturn(Optional.empty());
        String comment = commentService.deleteById(1);
        Assertions.assertTrue(comment.contains("Comment delete failed"));
    }

    @Test
    void updateIfCommentExistTest() {
        given(commentRepository.save(new Comment(1, 1, "newComment")))
                .willReturn(new Comment(1, 1, "newComment"));
        given(commentRepository.findById(1L)).willReturn(Optional.of(new Comment(1, 1, "newComment")));
        String comment = commentService.update(1, 1, "newComment");
        Assertions.assertTrue(comment.contains("Comment successfully updated"));
    }

    @Test
    void updateIfCommentNotExistTest() {
        given(commentRepository.findById(1L)).willReturn(Optional.empty());
        String comment = commentService.update(1, 1, "newComment");
        Assertions.assertTrue(comment.contains("Comment update failed"));
    }
}