package ru.otus.spring.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.homework.service.BookService;
import ru.otus.spring.homework.service.CommentService;

import java.util.List;


@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommand {

    private final BookService bookService;
    private final CommentService commentService;

    @ShellMethod(value = "Get book command", key = {"gb", "get book"})
    public String getBook(long id) {
        return bookService.getById(id);
    }

    @ShellMethod(value = "Get book comments command", key = {"gcb", "get book comments"})
    public List<String> getCommentsForBook(long id) {
        return bookService.getCommentsForBook(id);
    }

    @ShellMethod(value = "Get all books command", key = {"gab", "get all books"})
    public List<String> getAllBooks() {
        return bookService.getAll();
    }

    @ShellMethod(value = "Create book command", key = {"cb", "create book"})
    public String createBook(String bookName, long authorId, long genreId) {
        return bookService.create(bookName, authorId, genreId);
    }

    @ShellMethod(value = "Update book command", key = {"ub", "update book"})
    public String updateBook(long id, String bookName, long authorId, long genreId) {
        return bookService.update(id, bookName, authorId, genreId);
    }

    @ShellMethod(value = "Delete book command", key = {"db", "delete book"})
    public String deleteBook(long id) {
        return bookService.deleteById(id);
    }

    @ShellMethod(value = "Get comment command", key = {"gc", "get comment"})
    public String getComment(long id) {
        return commentService.getById(id);
    }

    @ShellMethod(value = "Get all comments command", key = {"gac", "get all comments"})
    public List<String> getAllComments() {
        return commentService.getAll();
    }

    @ShellMethod(value = "Create comment command", key = {"cc", "create comment"})
    public String createComment(long bookId, String comment) {
        return commentService.create(bookId, comment);
    }

    @ShellMethod(value = "Update comment command", key = {"uc", "update comment"})
    public String updateComment(long id, long bookId, String comment) {
        return commentService.update(id, bookId, comment);
    }

    @ShellMethod(value = "Delete comment command", key = {"dc", "delete comment"})
    public String deleteComment(long id) {
        return commentService.deleteById(id);
    }

}
