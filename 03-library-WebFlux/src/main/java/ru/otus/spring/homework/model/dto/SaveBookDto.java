package ru.otus.spring.homework.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveBookDto extends BookDto {
    private String authorId;
    private String authorName;
    private String authorSurname;
    private String genreId;
    private String genreName;
}
