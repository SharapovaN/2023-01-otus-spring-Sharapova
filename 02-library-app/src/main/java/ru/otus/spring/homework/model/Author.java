package ru.otus.spring.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class Author {
    @Id
    private String id;

    private String authorName;

    public Author(String authorName) {
        this.authorName = authorName;
    }
}
