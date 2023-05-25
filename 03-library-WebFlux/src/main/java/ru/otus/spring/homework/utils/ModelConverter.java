package ru.otus.spring.homework.utils;

import ru.otus.spring.homework.model.dto.AuthorDto;
import ru.otus.spring.homework.model.dto.BookDto;
import ru.otus.spring.homework.model.dto.CommentDto;
import ru.otus.spring.homework.model.dto.GenreDto;
import ru.otus.spring.homework.model.entity.Author;
import ru.otus.spring.homework.model.entity.Book;
import ru.otus.spring.homework.model.entity.Comment;
import ru.otus.spring.homework.model.entity.Genre;

import java.util.ArrayList;
import java.util.List;

public class ModelConverter {

    public static BookDto toBookDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setName(book.getBookName());
        dto.setAuthor(book.getAuthor().getAuthorName());
        dto.setGenre(book.getGenre().getGenreName());
        return dto;
    }

    public static BookDto toBookWithCommentsDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setName(book.getBookName());
        dto.setAuthor(book.getAuthor().getAuthorName());
        dto.setGenre(book.getGenre().getGenreName());
        List<String> comments = new ArrayList<>();
        List<Comment> commentList = book.getComments();
        for (Comment comment : commentList) {
            comments.add(comment.getComment());
        }
        dto.setComments(comments);
        return dto;
    }

    public static CommentDto toCommentDto(Comment comment) {
        CommentDto dto = new CommentDto();

        dto.setId(comment.getId());
        dto.setBookId(comment.getBook().getId());
        dto.setBookName(comment.getBook().getBookName());
        dto.setComment(comment.getComment());

        return dto;
    }

    public static AuthorDto toAuthorDto(Author author) {
        AuthorDto dto = new AuthorDto();
        dto.setId(author.getId());
        dto.setName(author.getAuthorName());
        return dto;
    }

    public static GenreDto toGenreDto(Genre genre) {
        GenreDto dto = new GenreDto();
        dto.setId(genre.getId());
        dto.setName(genre.getGenreName());
        return dto;
    }

}
