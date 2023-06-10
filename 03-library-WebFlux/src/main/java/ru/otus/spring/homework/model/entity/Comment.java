package ru.otus.spring.homework.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class Comment {
    @Id
    private String id;

    private Book book;

    private String comment;

    public Comment(Book book, String comment) {
        this.book = book;
        this.comment = comment;
    }

    public Comment(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id = '" + id + '\'' +
                ", book = " + book.getBookName() +
                ", comment = '" + comment + '\'' +
                '}';
    }
}
