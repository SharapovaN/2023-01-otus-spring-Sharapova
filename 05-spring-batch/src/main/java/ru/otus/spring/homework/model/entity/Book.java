package ru.otus.spring.homework.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class Book {

    @Id
    private Long id;

    private String bookName;

    private Author author;

    private Genre genre;

    @DBRef
    private List<Comment> comments;

    public Book(String bookName) {
        this.bookName = bookName;
    }

    public Book(String bookName, Author author, Genre genre) {
        this.bookName = bookName;
        this.author = author;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id = '" + id + '\'' +
                ", bookName = '" + bookName + '\'' +
                '}';
    }
}
