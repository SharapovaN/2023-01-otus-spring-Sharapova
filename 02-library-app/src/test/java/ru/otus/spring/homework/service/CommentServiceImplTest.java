package ru.otus.spring.homework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.homework.model.Book;
import ru.otus.spring.homework.model.Comment;
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
        comments.add(new Comment("642414e251c1e2380fb49abf", new Book("Captains daughter"), "comment1"));
        comments.add(new Comment("642414e251c1e2380fb49abg", new Book("Captains daughter"), "comment2"));
        given(commentRepository.findAll()).willReturn(comments);
        List<String> commentList = commentService.getAll();
        Assertions.assertEquals(2, commentList.size());
    }

    @Test
    void getByIdIfExistsTest() {
        given(commentRepository.findById("642414e251c1e2380fb49abg"))
                .willReturn(Optional.of(new Comment("642414e251c1e2380fb49abg", new Book("Captains daughter"),
                        "comment1")));
        String comment = commentService.getById("642414e251c1e2380fb49abg");
        Assertions.assertTrue(comment.contains("comment1"));
    }

    @Test
    void getByIdIfNotExistsTest() {
        given(commentRepository.findById("642414e251c1e2380fb49abg")).willReturn(Optional.empty());
        String comment = commentService.getById("642414e251c1e2380fb49abg");
        Assertions.assertTrue(comment.contains("not found"));
    }

    @Test
    void createIfOkTest() {
        given(commentRepository.save(new Comment(null, new Book("Captains daughter"),
                "comment1")))
                .willReturn(new Comment("642414e251c1e2380fb49abf", new Book("Captains daughter"), "comment1"));
        given(bookService.checkBookExists("642414e251c1e2380fb49abf")).willReturn(true);
        given(bookService.getBookById("642414e251c1e2380fb49abf")).willReturn(new Book("Captains daughter"));
        String comment = commentService.create("642414e251c1e2380fb49abf", "comment1");
        Assertions.assertTrue(comment.contains("Comment successfully created"));
    }

    @Test
    void createIfBookNotExistsTest() {
        given(bookService.checkBookExists("642414e251c1e2380fb49abf")).willReturn(false);
        String comment = commentService.create("642414e251c1e2380fb49abf", "comment1");
        Assertions.assertTrue(comment.contains("Comment creation failed"));
    }

    @Test
    void deleteByIdIfCommentExistTest() {
        given(commentRepository.findById("642414e251c1e2380fb49abf")).willReturn(Optional.of(new Comment("642414e251c1e2380fb49abf")));
        String comment = commentService.deleteById("642414e251c1e2380fb49abf");
        Assertions.assertTrue(comment.contains("Comment successfully deleted"));
    }

    @Test
    void deleteByIdIfCommentNotExistTest() {
        given(commentRepository.findById("642414e251c1e2380fb49abf")).willReturn(Optional.empty());
        String comment = commentService.deleteById("642414e251c1e2380fb49abf");
        Assertions.assertTrue(comment.contains("Comment delete failed"));
    }

    @Test
    void updateIfCommentExistTest() {
        given(commentRepository.save(new Comment("642414e251c1e2380fb49abf", new Book("Captains daughter"),
                "newComment")))
                .willReturn(new Comment("642414e251c1e2380fb49abf", new Book("Captains daughter"), "newComment"));
        given(commentRepository.findById("642414e251c1e2380fb49abf"))
                .willReturn(Optional.of(new Comment("642414e251c1e2380fb49abf", new Book("Captains daughter"),
                        "oldComment")));
        given(bookService.getBookById("642414e251c1e2380fb49ab8")).willReturn(new Book("Captains daughter"));
        String comment = commentService.update("642414e251c1e2380fb49abf", "642414e251c1e2380fb49ab8",
                "newComment");
        Assertions.assertTrue(comment.contains("Comment successfully updated"));
    }

    @Test
    void updateIfCommentNotExistTest() {
        given(commentRepository.findById("642414e251c1e2380fb49abf")).willReturn(Optional.empty());
        String comment = commentService.update("642414e251c1e2380fb49abf","642414e251c1e2380fb49ab8",
                "newComment");
        Assertions.assertTrue(comment.contains("Comment update failed"));
    }
}