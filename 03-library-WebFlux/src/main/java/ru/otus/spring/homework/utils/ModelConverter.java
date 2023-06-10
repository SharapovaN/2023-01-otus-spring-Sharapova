package ru.otus.spring.homework.utils;

import ru.otus.spring.homework.model.dto.*;
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
        dto.setAuthor(book.getAuthor().getAuthorName() + " " + book.getAuthor().getAuthorSurname());
        dto.setGenre(book.getGenre().getGenreName());
        return dto;
    }

    public static BookDto toBookWithCommentsDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setName(book.getBookName());
        dto.setAuthor(book.getAuthor().getAuthorName() + " " + book.getAuthor().getAuthorSurname());
        dto.setGenre(book.getGenre().getGenreName());
        List<String> comments = new ArrayList<>();
        List<Comment> commentList = book.getComments();
        if (commentList != null && !commentList.isEmpty()) {
            for (Comment comment : commentList) {
                comments.add(comment.getComment());
            }
        }
        dto.setComments(comments);
        return dto;
    }

    public static AuthorDto toAuthorDto(Author author) {
        AuthorDto dto = new AuthorDto();
        dto.setId(author.getId());
        dto.setName(author.getAuthorName());
        dto.setSurname(author.getAuthorSurname());
        return dto;
    }

    public static GenreDto toGenreDto(Genre genre) {
        GenreDto dto = new GenreDto();
        dto.setId(genre.getId());
        dto.setName(genre.getGenreName());
        return dto;
    }

    public static Book toBook(SaveBookDto bookDto) {
        Book book = new Book(bookDto.getName());
        book.setId(bookDto.getId());
        book.setAuthor(new Author(bookDto.getAuthorId(), bookDto.getAuthorName(), bookDto.getAuthorSurname()));
        book.setGenre(new Genre(bookDto.getGenreId(), bookDto.getGenreName()));
        return book;
    }

}
