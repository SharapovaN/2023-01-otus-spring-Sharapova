package ru.otus.spring.homework.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.homework.model.entity.Author;
import ru.otus.spring.homework.model.entity.Genre;
import ru.otus.spring.homework.repository.AuthorRepository;
import ru.otus.spring.homework.repository.GenreRepository;

@ChangeLog
public class DatabaseChangelog {

    private Author pushkin;
    private Author tolkien;
    private Genre novel;
    private Genre fantasy;

    @ChangeSet(order = "001", id = "dropDb", author = "nsharapova", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "nsharapova")
    public void insertAuthors(AuthorRepository authorRepository) {
        pushkin = authorRepository.save(new Author("642414e251c1e2380fb49ab1", "Aleksandr", "Pushkin")).block();
        tolkien = authorRepository.save(new Author("642414e251c1e2380fb49ab2", "John", " Tolkien")).block();
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "nsharapova")
    public void insertGenres(GenreRepository genreRepository) {
        novel = genreRepository.save(new Genre("Historical Novel")).block();
        fantasy = genreRepository.save(new Genre("Fantasy")).block();
    }
}

