package ru.otus.spring.homework.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveBookDto extends BookDto {
    private Long authorId;
    private Long genreId;
}
