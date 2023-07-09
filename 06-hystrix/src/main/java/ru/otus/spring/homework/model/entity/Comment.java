package ru.otus.spring.homework.model.entity;

import jakarta.persistence.*;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @Column(name = "comment")
    private String comment;

    public Comment(Book book, String comment) {
        this.book = book;
        this.comment = comment;
    }

    public Comment(long id) {
        this.id = id;
    }
}
