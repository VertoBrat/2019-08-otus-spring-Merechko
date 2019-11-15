package ru.photorex.apiserver.handler;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.photorex.apiserver.model.Author;
import ru.photorex.apiserver.model.Book;
import ru.photorex.apiserver.repository.BookRepository;
import ru.photorex.apiserver.to.BookTo;
import ru.photorex.apiserver.to.mapper.BookMapper;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Обработчик запросов для книг ")
@SpringBootTest
public class BookHandlerTest {

    private WebTestClient webTestClient;
    private static Author AUTHOR = new Author("firstName", "lastName");
    private static Book BOOK = new Book("1", "title", "content",
            Sets.newHashSet("genre"), Sets.newHashSet(AUTHOR), null);

    @Autowired
    RouterFunction routerFunction;

    @Autowired
    BookMapper mapper;

    @MockBean
    BookRepository repository;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }

    @DisplayName(" должен возвращать все книги")
    @Test
    void shouldGetPagedBooks() {
        given(repository.findAll()).willReturn(Flux.just(BOOK));

        webTestClient.get()
                .uri("/books?page=0")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("title");

        verify(repository, times(1)).findAll();

    }

    @DisplayName(" должен возвращать книгу по id")
    @Test
    void shouldReturnBookById() {
        given(repository.findById("1")).willReturn(Mono.just(BOOK));

        webTestClient.get()
                .uri("/books/{id}", "1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("1");

        verify(repository, times(1)).findById("1");
    }

    @DisplayName(" должен возвращать фильтрованный список книг по жанру")
    @Test
    void shouldReturnFilteredBookByGenre() {
        given(repository.findAllFilteredPerGenre("genre")).willReturn(Flux.just(BOOK));

        webTestClient.get()
                .uri("/books?search={text}&type={type}", "genre", "genre")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("title");
    }

    @DisplayName(" должен возвращать фильтрованный список книг по автору")
    @Test
    void shouldReturnFilteredBookByAuthor() {
        given(repository.findAllFilteredPerAuthors(AUTHOR)).willReturn(Flux.just(BOOK));

        webTestClient.get()
                .uri("/books?search={text}&type={type}", "firstName lastName", "author")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("title");
    }

    @DisplayName(" должен сохранять книгу")
    @Test
    void shouldSaveBook() {
        BookTo to = mapper.toTo(BOOK);
        given(repository.save(BOOK)).willReturn(Mono.just(BOOK));

        webTestClient.post()
                .uri("/books")
                .body(Mono.just(to), BookTo.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("title");
    }

    @DisplayName(" должен обновлять книгу")
    @Test
    void shouldUpdateBook() {
        BookTo toBook = mapper.toTo(BOOK);
        toBook.setTitle("newTitle");
        Book dbBook = mapper.toEntity(toBook);
        given(repository.findById("1")).willReturn(Mono.just(dbBook));
        given(repository.save(dbBook)).willReturn(Mono.just(dbBook));

        webTestClient.put()
                .uri("/books/{id}", "1")
                .body(Mono.just(toBook), BookTo.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("newTitle");
    }

    @DisplayName(" должен удалять книгу по id")
    @Test
    void shouldDeleteBook() {
        given(repository.deleteById("1")).willReturn(Mono.empty());

        webTestClient.delete()
                .uri("/books/{id}", "1")
                .exchange()
                .expectStatus()
                .isNoContent();
    }
}
