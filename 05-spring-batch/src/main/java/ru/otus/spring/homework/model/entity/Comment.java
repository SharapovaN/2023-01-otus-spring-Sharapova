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
    private Long id;

    private Book book;

    private String comment;

    @Override
    public String toString() {
        return "Comment{" +
                "id = '" + id + '\'' +
                ", book = " + book.getBookName() +
                ", comment = '" + comment + '\'' +
                '}';
    }
}
