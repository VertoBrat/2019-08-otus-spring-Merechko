package ru.photorex.apiserver.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Mono;
import ru.photorex.apiserver.model.Comment;
import ru.photorex.apiserver.repository.CommentRepository;
import ru.photorex.apiserver.to.CommentTo;
import ru.photorex.apiserver.to.mapper.CommentMapper;

import static org.mockito.BDDMockito.given;

@DisplayName("Обработчик запросов для комментов")
@SpringBootTest
public class CommentHandlerTest {

    private WebTestClient webTestClient;

    @Autowired
    RouterFunction routerFunction;

    @Autowired
    CommentMapper mapper;

    @MockBean
    CommentRepository repository;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }

    @DisplayName(" должен сохранять коммент")
    @Test
    void shouldSaveComment() {
        CommentTo to = new CommentTo(null, "text", null, "1");
        Comment comment = mapper.toEntity(to);
        given(repository.save(comment)).willReturn(Mono.just(comment));

        webTestClient.post()
                .uri("/comments")
                .body(Mono.just(to), CommentTo.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("text");
    }

    @DisplayName(" должен обновлять коммент")
    @Test
    void shouldUpdateComment() {
        CommentTo to = new CommentTo(null, "text", null, "1");
        Comment comment = mapper.toEntity(to);
        comment.setText("newText");
        given(repository.findById("1")).willReturn(Mono.just(comment));
        given(repository.save(comment)).willReturn(Mono.just(comment));

        webTestClient.put()
                .uri("/comments/{id}", "1")
                .body(Mono.just(to), CommentTo.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("newText");
    }

    @DisplayName(" должен удалять коммент")
    @Test
    void shouldDeleteComment() {
        given(repository.deleteById("1")).willReturn(Mono.empty());

        webTestClient.delete()
                .uri("/comments/{id}", "1")
                .exchange()
                .expectStatus()
                .isNoContent();
    }
}
