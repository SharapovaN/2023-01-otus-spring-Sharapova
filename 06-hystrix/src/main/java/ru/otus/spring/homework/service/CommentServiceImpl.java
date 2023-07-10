package ru.otus.spring.homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.exception.BookNotFoundException;
import ru.otus.spring.homework.exception.CommentNotFoundException;
import ru.otus.spring.homework.model.dto.CommentDto;
import ru.otus.spring.homework.model.entity.Comment;
import ru.otus.spring.homework.repository.CommentRepository;
import ru.otus.spring.homework.utils.ModelConverter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final BookService bookService;
    private final CommentRepository commentRepository;

    @HystrixCommand(commandKey = "findAllComments", fallbackMethod = "getUndefinedCommentsList")
    @Override
    public List<CommentDto> getAll() {
        return commentRepository.findAll().stream().map(ModelConverter::toCommentDto).collect(Collectors.toList());
    }

    @HystrixCommand(commandKey = "findCommentById", fallbackMethod = "getUndefinedComment")
    @Override
    public CommentDto getById(long id) {
        return commentRepository.findById(id).map(ModelConverter::toCommentDto)
                .orElseThrow(() -> new CommentNotFoundException(id));
    }

    @HystrixCommand(commandKey = "findCommentsByBookId", fallbackMethod = "getUndefinedCommentsList")
    @Override
    public List<CommentDto> getByBookId(long id) {
        return commentRepository.findByBookId(id).stream().map(ModelConverter::toCommentDto).collect(Collectors.toList());
    }

    @Override
    public Comment create(CommentDto comment) {
        if (bookService.checkBookExists(comment.getBookId())) {
            return commentRepository.save(new Comment(bookService.getById(comment.getBookId()), comment.getComment()));
        }
        throw new BookNotFoundException(comment.getBookId());
    }

    @Override
    public void deleteById(long id) {
        if (commentRepository.findById(id).isPresent()) {
            commentRepository.delete(new Comment(id));
        } else {
            throw new CommentNotFoundException(id);
        }
    }

    @Override
    public Comment update(CommentDto comment) {
        Optional<Comment> optionalComment = commentRepository.findById(comment.getId());
        if (optionalComment.isPresent()) {
            Comment updateComment = optionalComment.get();
            updateComment.setBook(bookService.getById(comment.getBookId()));
            updateComment.setComment(comment.getComment());
            return commentRepository.save(updateComment);
        }
        throw new CommentNotFoundException(comment.getId());
    }


    private List<CommentDto> getUndefinedCommentsList() {
        return List.of(new CommentDto(0, 0, "N/A", "null"));
    }

    private List<CommentDto> getUndefinedCommentsList(long bookId) {
        return List.of(new CommentDto(0, 0, "N/A", "null"));
    }

    private CommentDto getUndefinedComment(long commentId) {
        return new CommentDto(0, 0, "N/A", "null");
    }
}
