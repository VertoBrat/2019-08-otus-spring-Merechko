package ru.photorex.apiserver.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import ru.photorex.apiserver.model.Comment;

@DisplayName("Реактивный репозиторий для работы с комментариями")
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.photorex.apiserver.config", "ru.photorex.apiserver.repository", "ru.photorex.apiserver.events"})
public class CommentRepositoryTest {

    @Autowired
    CommentRepository repository;

    @Autowired
    ReactiveMongoTemplate mongoTemplate;

    @DisplayName(" должен удалять комментарий из коллекции и книги")
    @DirtiesContext
    @Test
    void shouldDeleteCommentByBookId() {
        Flux<Comment> bookFlux = repository.findAll()
                .elementAt(0)
                .map(Comment::getBook)
                .flatMap(b -> mongoTemplate.remove(b))
                .flux()
                .flatMap(d -> mongoTemplate.findAll(Comment.class));

        StepVerifier
                .create(bookFlux)
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }
}
