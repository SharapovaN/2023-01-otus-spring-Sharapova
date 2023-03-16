package ru.otus.spring.homework.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "book_id")
    private long bookId;

    @Column(name = "comment")
    private String comment;

    public Comment(long bookId, String comment) {
        this.bookId = bookId;
        this.comment = comment;
    }

    public Comment(long id) {
        this.id = id;
    }
}
