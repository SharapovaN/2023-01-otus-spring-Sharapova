package ru.otus.spring.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book {
    private long id;
    private String bookName;
    private Author author;
    private Genre genre;
}
