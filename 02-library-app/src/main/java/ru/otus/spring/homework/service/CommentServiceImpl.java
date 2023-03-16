package ru.otus.spring.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.homework.model.Comment;
import ru.otus.spring.homework.repository.CommentRepository;
import ru.otus.spring.homework.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final BookService bookService;
    private final CommentRepository commentRepository;

    @Override
    public List<String> getAll() {
        List<String> comments = commentRepository.findAll().stream().map(Comment::toString).toList();
        return comments.size() != 0 ? comments : new ArrayList<>(Collections.singleton(StringUtils.COMMENTS_NOT_FOUND_RESPONSE));
    }

    @Override
    public String getById(long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.isPresent() ? comment.get().toString() : StringUtils.COMMENT_NOT_FOUND_RESPONSE;
    }

    @Transactional
    @Override
    public String create(long bookId, String comment) {
        if (bookService.checkBookExists(bookId)) {
            commentRepository.saveOrUpdate(new Comment(bookId, comment));
            return StringUtils.COMMENT_CREATED_RESPONSE;
        }
        return StringUtils.COMMENT_NOT_CREATED_RESPONSE;
    }

    @Transactional
    @Override
    public String deleteById(long id) {
        if (commentRepository.findById(id).isPresent()) {
            commentRepository.delete(new Comment(id));
            return StringUtils.COMMENT_DELETE_RESPONSE;
        }
        return StringUtils.COMMENT_NOT_DELETE_RESPONSE;
    }

    @Transactional
    @Override
    public String update(long id, long bookId, String comment) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Comment updateComment = optionalComment.get();
            updateComment.setBookId(bookId);
            updateComment.setComment(comment);
            commentRepository.saveOrUpdate(updateComment);
            return StringUtils.COMMENT_UPDATED_RESPONSE;
        }
        return StringUtils.COMMENT_NOT_UPDATED_RESPONSE;
    }
}
