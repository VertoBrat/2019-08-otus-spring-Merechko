package ru.photorex.apiserver.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.photorex.apiserver.model.Author;
import ru.photorex.apiserver.model.Book;
import ru.photorex.apiserver.model.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Реактивный Репозиторий для работы с книгами")
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.photorex.apiserver.config", "ru.photorex.apiserver.repository", "ru.photorex.apiserver.events"})
public class BookRepositoryTest {

    @Autowired
    ReactiveMongoTemplate mongoTemplate;

    @Autowired
    BookRepository repository;

    @DisplayName(" должен сохранять комментарий в книге")
    @DirtiesContext
    @Test
    void shouldSaveCommentInBookCommentsArray() {
        Mono<Book> bookMono = mongoTemplate.findAll(Book.class)
                .elementAt(0)
                .flatMap(b -> mongoTemplate.save(new Comment("text", b)))
                .flatMap(c -> mongoTemplate.findById(c.getBook().getId(), Book.class));

        StepVerifier
                .create(bookMono)
                .assertNext(b -> assertThat(b.getComments().size()).isEqualTo(2))
                .expectComplete()
                .verify();
    }

    @DisplayName(" должен удалять комментарии из книг при удалении комментария из коллекции")
    @DirtiesContext
    @Test
    void shouldDeleteCommentInsideBook() {
        Mono<Book> bookMono = repository.findAll()
                .elementAt(0)
                .map(b -> b.getComments().get(0))
                .flatMap(c -> mongoTemplate.findAndRemove(Query.query(Criteria.where("_id").is(c.getId())), Comment.class))
                .flatMap(c -> mongoTemplate.findById(c.getBook().getId(), Book.class));

        StepVerifier
                .create(bookMono)
                .assertNext(b -> b.getComments().isEmpty())
                .expectComplete()
                .verify();
    }

    @DisplayName(" должен возвращать все книги по фильтру жанра")
    @Test
    void shouldReturnFilteredBooksByGenreName() {
        Flux<Book> bookFlux = repository.findAllFilteredPerGenre("Genre1");

        StepVerifier
                .create(bookFlux)
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }

    @DisplayName(" должен возвращать все книги по фильтру автора")
    @Test
    void shouldReturnFilteredBooksByAuthor() {
        Flux<Book> bookFlux = repository.findAllFilteredPerAuthors(new Author("FirstName1", "LastName1"));

        StepVerifier
                .create(bookFlux)
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }

    @DisplayName(" должен получать все жанры")
    @Test
    void shouldReturnAllGenres() {
        Flux<String> genres = repository.findAllGenres();

        StepVerifier
                .create(genres)
                .expectNext("Genre1", "Genre2", "Genre3")
                .expectComplete()
                .verify();
    }

    @DisplayName(" должен получать всех авторов")
    @Test
    void shouldReturnAllAuthors() {
        Flux<Author> authors = repository.findAllAuthors();

        StepVerifier
                .create(authors)
                .expectNextCount(4)
                .expectComplete()
                .verify();
    }

}
