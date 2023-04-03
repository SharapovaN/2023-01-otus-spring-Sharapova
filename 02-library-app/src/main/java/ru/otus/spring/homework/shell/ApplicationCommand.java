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
    public String getBook(String id) {
        return bookService.getById(id);
    }

    @ShellMethod(value = "Get book comments command", key = {"gbc", "get book comments"})
    public List<String> getCommentsForBook(String id) {
        return bookService.getCommentsForBook(id);
    }

    @ShellMethod(value = "Get all books command", key = {"gab", "get all books"})
    public List<String> getAllBooks() {
        return bookService.getAll();
    }

    @ShellMethod(value = "Create book command", key = {"cb", "create book"})
    public String createBook(String bookName, String authorId, String genreName) {
        return bookService.create(bookName, authorId, genreName);
    }

    @ShellMethod(value = "Update book command", key = {"ub", "update book"})
    public String updateBook(String id, String bookId, String authorId, String genreName) {
        return bookService.update(id, bookId, authorId, genreName);
    }

    @ShellMethod(value = "Delete book command", key = {"db", "delete book"})
    public String deleteBook(String id) {
        return bookService.deleteById(id);
    }

    @ShellMethod(value = "Get comment command", key = {"gc", "get comment"})
    public String getComment(String id) {
        return commentService.getById(id);
    }

    @ShellMethod(value = "Get all comments command", key = {"gac", "get all comments"})
    public List<String> getAllComments() {
        return commentService.getAll();
    }

    @ShellMethod(value = "Create comment command", key = {"cc", "create comment"})
    public String createComment(String bookId, String comment) {
        return commentService.create(bookId, comment);
    }

    @ShellMethod(value = "Update comment command", key = {"uc", "update comment"})
    public String updateComment(String id, String bookId, String comment) {
        return commentService.update(id, bookId, comment);
    }

    @ShellMethod(value = "Delete comment command", key = {"dc", "delete comment"})
    public String deleteComment(String id) {
        return commentService.deleteById(id);
    }

}
