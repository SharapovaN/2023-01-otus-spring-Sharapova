package ru.otus.spring.homework.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "books")
@NamedEntityGraph(name = "book-author-genre-entity-graph",
        attributeNodes = {@NamedAttributeNode("author"), @NamedAttributeNode("genre")})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "book_name")
    private String bookName;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private List<Comment> comments;

    public Book(String bookName) {
        this.bookName = bookName;
    }

    public Book(long id, String bookName) {
        this.id = id;
        this.bookName = bookName;
    }
}
