package ru.otus.spring.homework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.homework.exception.BookNotFoundException;
import ru.otus.spring.homework.exception.CommentNotFoundException;
import ru.otus.spring.homework.model.dto.CommentDto;
import ru.otus.spring.homework.model.entity.Book;
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
        comments.add(new Comment(1, new Book(1), "comment1"));
        comments.add(new Comment(2, new Book(1), "comment2"));
        given(commentRepository.findAll()).willReturn(comments);
        List<CommentDto> commentList = commentService.getAll();
        Assertions.assertEquals(2, commentList.size());
    }

    @Test
    void getByIdIfExistsTest() {
        given(commentRepository.findById(1L)).willReturn(Optional.of(new Comment(1, new Book(1), "comment1")));
        CommentDto comment = commentService.getById(1);
        Assertions.assertEquals("comment1", comment.getComment());
    }

    @Test
    void getByIdIfNotExistsTest() {
        given(commentRepository.findById(1L)).willReturn(Optional.empty());
        Assertions.assertThrows(CommentNotFoundException.class, () -> commentService.getById(1));
    }

    @Test
    void createIfOkTest() {
        Book book = new Book(1);
        given(commentRepository.save(new Comment(book, "comment1")))
                .willReturn(new Comment(1, book, "comment1"));
        given(bookService.checkBookExists(1)).willReturn(true);
        given(bookService.getById(1)).willReturn(book);

        CommentDto commentDto = new CommentDto();
        commentDto.setBookId(1L);
        commentDto.setComment("comment1");

        CommentDto comment = commentService.create(commentDto);
        Assertions.assertNotNull(comment);
        Assertions.assertEquals("comment1", comment.getComment());
    }

    @Test
    void createIfBookNotExistsTest() {
        given(bookService.checkBookExists(3)).willReturn(false);

        CommentDto commentDto = new CommentDto();
        commentDto.setBookId(3L);
        commentDto.setComment("comment1");

        Assertions.assertThrows(BookNotFoundException.class, () -> commentService.create(commentDto));
    }

    @Test
    void deleteByIdIfCommentNotExistTest() {
        given(commentRepository.findById(1L)).willReturn(Optional.empty());
        Assertions.assertThrows(CommentNotFoundException.class, () -> commentService.deleteById(1));
    }

    @Test
    void updateIfCommentExistTest() {
        given(commentRepository.save(new Comment(1, new Book(1), "newComment")))
                .willReturn(new Comment(1, new Book(1), "newComment"));
        given(commentRepository.findById(1L)).willReturn(Optional.of(new Comment(1, new Book(1), "newComment")));
        given(bookService.getById(1)).willReturn(new Book(1));

        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setBookId(1L);
        commentDto.setComment("newComment");

        CommentDto comment = commentService.update(commentDto);
        Assertions.assertNotNull(comment);
        Assertions.assertEquals("newComment", comment.getComment());
    }

    @Test
    void updateIfCommentNotExistTest() {
        given(commentRepository.findById(1L)).willReturn(Optional.empty());

        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setBookId(1L);
        commentDto.setComment("comment1");

        Assertions.assertThrows(CommentNotFoundException.class, () -> commentService.update(commentDto));
    }

    @Test
    void getByBookId() {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1, new Book(1), "comment1"));
        comments.add(new Comment(2, new Book(1), "comment2"));

        given(commentRepository.findByBookId(1)).willReturn(comments);
        List<CommentDto> commentDtos = commentService.getByBookId(1);

        Assertions.assertEquals(2, commentDtos.size());
        Assertions.assertEquals("comment1", commentDtos.get(0).getComment());
    }
}