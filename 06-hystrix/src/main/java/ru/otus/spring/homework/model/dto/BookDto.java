package ru.otus.spring.homework.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private long id;
    private String name;
    private String author;
    private String genre;
    List<String> comments;
}
