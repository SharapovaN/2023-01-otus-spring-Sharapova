package ru.otus.spring.homework.service;

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

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final BookService bookService;
    private final CommentRepository commentRepository;

    @Override
    public List<CommentDto> getAll() {
        return commentRepository.findAll().stream().map(ModelConverter::toCommentDto).toList();
    }

    @Override
    public CommentDto getById(long id) {
        return commentRepository.findById(id).map(ModelConverter::toCommentDto)
                .orElseThrow(() -> new CommentNotFoundException(id));
    }

    @Override
    public Comment create(CommentDto comment) {
        if (bookService.checkBookExists(comment.getBookId())) {
            return commentRepository.save(new Comment(comment.getBookId(), comment.getComment()));
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
            updateComment.setBookId(comment.getBookId());
            updateComment.setComment(comment.getComment());
            return commentRepository.save(updateComment);
        }
        throw new CommentNotFoundException(comment.getId());
    }
}
