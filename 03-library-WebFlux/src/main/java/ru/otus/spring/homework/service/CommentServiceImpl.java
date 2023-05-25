package ru.otus.spring.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    /*private final BookService bookService;
    private final CommentRepository commentRepository;

    @Override
    public List<String> getAll() {
        List<String> comments = commentRepository.findAll().stream().map(Comment::toString).toList();
        return comments.size() != 0 ? comments : new ArrayList<>(Collections.singleton(StringUtils.COMMENTS_NOT_FOUND_RESPONSE));
    }

    @Override
    public String getById(String id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.isPresent() ? comment.get().toString() : StringUtils.COMMENT_NOT_FOUND_RESPONSE;
    }

    @Override
    public String create(String bookId, String comment) {
        if (bookService.checkBookExists(bookId)) {
            Book book = bookService.getBookById(bookId);
            Comment savedComment = commentRepository.save(new Comment(null, book, comment));
            return StringUtils.COMMENT_CREATED_RESPONSE + savedComment.getId();
        }
        return StringUtils.COMMENT_NOT_CREATED_RESPONSE;
    }

    @Override
    public String deleteById(String id) {
        if (commentRepository.findById(id).isPresent()) {
            commentRepository.delete(new Comment(id));
            return StringUtils.COMMENT_DELETE_RESPONSE;
        }
        return StringUtils.COMMENT_NOT_DELETE_RESPONSE;
    }

    @Override
    public String update(String id, String bookId, String comment) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Book book = bookService.getBookById(bookId);
            Comment updateComment = optionalComment.get();
            updateComment.setBook(book);
            updateComment.setComment(comment);
            commentRepository.save(updateComment);
            return StringUtils.COMMENT_UPDATED_RESPONSE;
        }
        return StringUtils.COMMENT_NOT_UPDATED_RESPONSE;
    }*/
}
