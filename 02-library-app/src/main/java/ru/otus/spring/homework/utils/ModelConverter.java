package ru.otus.spring.homework.utils;

import ru.otus.spring.homework.model.dto.BookDto;
import ru.otus.spring.homework.model.dto.SaveBookDto;
import ru.otus.spring.homework.model.entity.Book;
import ru.otus.spring.homework.model.entity.Comment;

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

    public static SaveBookDto toSaveBookDto(Book book) {
        SaveBookDto dto = new SaveBookDto();
        dto.setId(book.getId());
        dto.setName(book.getBookName());
        dto.setAuthor(book.getAuthor().getAuthorName());
        dto.setGenre(book.getGenre().getGenreName());
        dto.setAuthorId(book.getAuthor().getId());
        dto.setGenreId(book.getGenre().getId());
        return dto;
    }

}
