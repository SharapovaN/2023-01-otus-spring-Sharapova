package ru.otus.spring.homework.model;

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

    @Override
    public String toString() {
        return "Book{" +
                "id = '" + id + '\'' +
                ", bookName = '" + bookName + '\'' +
                '}';
    }
}
