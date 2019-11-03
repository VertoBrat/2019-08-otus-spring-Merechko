package ru.photorex.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.photorex.server.model.Book;
import ru.photorex.server.to.AuthorTo;
import ru.photorex.server.to.BookTo;
import ru.photorex.server.to.GenreTo;
import ru.photorex.server.to.mapper.BookMapper;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер для работы с книгами ")
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    private static final GenreTo GENRE = new GenreTo("Genre#1");
    private static final AuthorTo AUTHOR = new AuthorTo("Test Testing");
    private static final String NEW_TITLE = "New title";
    private static final String MAIN_URL = "/books";
    private static final String MAIN_URL_ID = "/books/{id}";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    BookMapper bookMapper;

    @DisplayName(" должен возвращать все книги постранично")
    @Test
    void shouldReturnPagedBooks() throws Exception {
        mockMvc.perform(get(MAIN_URL).param("page", "0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("_embedded.books", hasSize(1)))
                .andExpect(jsonPath("page.totalElements", is(3)))
                .andReturn();
    }

    @DisplayName(" должен вернуть все книги по фильтру жанра")
    @Test
    void shouldReturnFilteredBooksByGenre() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("search", "Genre#1");
        map.add("type", "genre");
        mockMvc.perform(get(MAIN_URL).params(map))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("_embedded.books", hasSize(2)))
                .andReturn();
    }

    @DisplayName(" должен вернуть все книги по фильтру автора")
    @Test
    void shouldReturnFilteredBooksByAuthor() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("search", "LastName#1");
        map.add("type", "author");
        mockMvc.perform(get(MAIN_URL).params(map))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.books", hasSize(2)))
                .andReturn();
    }

    @DisplayName(" должен возвращать noContent() при ненайденных фильтром книг")
    @Test
    void shouldReturnNoContentResponse() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("search", "R");
        map.add("type", "author");
        mockMvc.perform(get(MAIN_URL).params(map))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @DisplayName(" должен возвращать книгу по id")
    @Test
    void shouldReturnBookById() throws Exception {
        Book book = mongoOperations.findAll(Book.class).get(0);
        String id = book.getId();
        mockMvc.perform(get(MAIN_URL_ID, id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(id)))
                .andExpect(jsonPath("_links", is(notNullValue())))
                .andReturn();
    }

    @DisplayName(" должен сохранять книгу")
    @Test
    @DirtiesContext()
    void shouldSaveBook() throws Exception {
        BookTo to = new BookTo(null, "title", null, Set.of(GENRE), Set.of(AUTHOR), null);
        mockMvc.perform(post(MAIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(to)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", is(notNullValue())))
                .andExpect(header().exists("Location"))
                .andReturn();
    }

    @DisplayName(" должен обновлять книгу")
    @Test
    @DirtiesContext()
    void shouldUpdateBook() throws Exception {
        Book book = mongoOperations.findAll(Book.class).get(0);
        BookTo to = bookMapper.toTo(book);
        to.setTitle(NEW_TITLE);
        to.setComments(null);
        mockMvc.perform(patch(MAIN_URL_ID, book.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(to)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("title", is(NEW_TITLE)))
                .andReturn();
    }

    @DisplayName(" должен удалять книгу")
    @Test
    @DirtiesContext()
    void shouldDeleteBookById() throws Exception {
        List<Book> books = mongoOperations.findAll(Book.class);
        int expectedListSize = books.size() - 1;
        Book book = books.get(0);
        String bookId = book.getId();
        mockMvc.perform(delete(MAIN_URL_ID, bookId))
                .andDo(print())
                .andExpect(status().isNoContent());
        int actualListSize = mongoOperations.findAll(Book.class).size();
        assertThat(actualListSize).isEqualTo(expectedListSize);

    }
}
