package ru.photorex.apiserver.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Flux;
import ru.photorex.apiserver.model.Author;
import ru.photorex.apiserver.repository.BookRepository;

import static org.mockito.BDDMockito.given;

@DisplayName("Обработчик запросов для авторов ")
@SpringBootTest
public class AuthorHandlerTest {

    private WebTestClient webTestClient;

    @Autowired
    RouterFunction routerFunction;

    @MockBean
    BookRepository repository;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }

    @DisplayName(" должен возвращать всех авторов")
    @Test
    void shouldReturnAuthors() {
        Author author = new Author("firstName", "lastName");
        given(repository.findAllAuthors()).willReturn(Flux.just(author));

        webTestClient.get()
                .uri("/authors")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("lastName");
    }
}
