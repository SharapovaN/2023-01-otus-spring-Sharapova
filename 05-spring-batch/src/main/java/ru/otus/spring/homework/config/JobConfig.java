package ru.otus.spring.homework.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.spring.homework.model.entity.Author;
import ru.otus.spring.homework.model.entity.Book;
import org.springframework.batch.item.ItemProcessor;
import ru.otus.spring.homework.model.entity.Genre;

import javax.sql.DataSource;
import java.util.HashMap;

@SuppressWarnings("unused")
@Configuration
@RequiredArgsConstructor
public class JobConfig {

    private final DataSource dataSource;

    private final JobRepository jobRepository;

    private final MongoTemplate mongoTemplate;

    private final PlatformTransactionManager transactionManager;

    @StepScope
    public MongoItemReader<Author> authorMongoItemReader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Author>()
                .name("authorItemReaded")
                .template(mongoTemplate)
                .targetType(Author.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public ItemProcessor<Author, Author> authorItemProcessor() {
        return author -> author;
    }

    @Bean
    public JdbcBatchItemWriter<Author> authorJdbcBatchItemWriter() {
        JdbcBatchItemWriter<Author> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO authors (id,author_name,author_surname) VALUES (:id,:authorName,:authorSurname)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public MongoItemReader<Genre> genreMongoItemReader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Genre>()
                .name("genreMongoItemReader")
                .template(mongoTemplate)
                .targetType(Genre.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public ItemProcessor<Genre, Genre> genreItemProcessor() {
        return genre -> genre;
    }

    @Bean
    public JdbcBatchItemWriter<Genre> genreJdbcBatchItemWriter() {
        JdbcBatchItemWriter<Genre> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO genres (id,genre_name) VALUES (:id,:genreName)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public MongoItemReader<Book> bookMongoItemReader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Book>()
                .name("bookMongoItemReader")
                .template(mongoTemplate)
                .targetType(Book.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public ItemProcessor<Book, Book> bookItemProcessor() {
        return book -> book;
    }

    @Bean
    public JdbcBatchItemWriter<Book> bookJdbcBatchItemWriter() {
        JdbcBatchItemWriter<Book> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO books (id,book_name,genre_id,author_id) VALUES (:id,:bookName,:genre.id,:author.id)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public Step authorStep() {
        return new StepBuilder("authorStep", jobRepository)
                .<Author, Author>chunk(10, transactionManager)
                .reader(authorMongoItemReader(mongoTemplate))
                .processor(authorItemProcessor())
                .writer(authorJdbcBatchItemWriter())
                .build();
    }

    @Bean
    public Step genreStep() {
        return new StepBuilder("genreStep", jobRepository)
                .<Genre, Genre>chunk(10, transactionManager)
                .reader(genreMongoItemReader(mongoTemplate))
                .processor(genreItemProcessor())
                .writer(genreJdbcBatchItemWriter())
                .build();
    }

    @Bean
    public Step bookStep() {
        return new StepBuilder("bookStep", jobRepository)
                .<Book, Book>chunk(10, transactionManager)
                .reader(bookMongoItemReader(mongoTemplate))
                .processor(bookItemProcessor())
                .writer(bookJdbcBatchItemWriter())
                .build();
    }

    @Bean
    public Job migrateMongoToMySql(Step authorStep, Step genreStep, Step bookStep) {
        return new JobBuilder("migrateMongoToMySql", jobRepository)
                .start(authorStep)
                .next(genreStep)
                .next(bookStep)
                .build();
    }
}
