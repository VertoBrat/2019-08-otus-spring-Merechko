package ru.photorex.hw12.controller;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import ru.photorex.hw12.model.Book;
import ru.photorex.hw12.to.BookTo;
import ru.photorex.hw12.to.Filter;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер для работы с книгами ")
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    private static final String INDEX_STRING = "Книги";
    private static final String BOOK_LIST_STRING = "Добавить книгу";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MongoOperations mongoOperations;

    @DisplayName(" должен возвращать главную страницу по запросу")
    @Test
    void shouldReturnIndexPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(content().string(containsString(INDEX_STRING)))
                .andReturn();
    }

    @DisplayName(" должен возвращать страницу с книгами по запросу")
    @Test
    @WithMockUser(username = "user")
    void shouldReturnBookListPage() throws Exception {
        mockMvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("book/list"))
                .andExpect(content().string(containsString(BOOK_LIST_STRING)))
                .andReturn();
    }

    @DisplayName(" должен возвращать страницу с книгой по запросу")
    @Test
    @WithUserDetails(userDetailsServiceBeanName = "userDetailsServiceImpl")
    void shouldReturnBookPage() throws Exception {
        val book = mongoOperations.findAll(Book.class).get(0);
        String title = book.getTitle();
        mockMvc.perform(get("/books/{id}", book.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("book/view"))
                .andExpect(content().string(containsString(title)))
                .andReturn();
    }

    @DisplayName(" должен возвращать страницу редактирования книги по запросу")
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    void shouldReturnEditBookPage() throws Exception {
        val book = mongoOperations.findAll(Book.class).get(0);
        String title = book.getTitle();
        mockMvc.perform(get("/books/edit/{id}", book.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("book/edit"))
                .andExpect(content().string(containsString(title)))
                .andReturn();
    }

    @DisplayName(" должен получать to и перенаправлять запрос")
    @Test
    @WithMockUser(username = "user")
    void shouldTakeToAndRedirectRequest() throws Exception {
        mockMvc.perform(post("/books")
                .param("id", "")
                .param("title", "Title")
                .param("genres","Story")
                .param("authors", "FirstName LastName")
                .flashAttr("bookTo", new BookTo()))
                .andDo(print())
                .andExpect(view().name("redirect:/books"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    @DisplayName(" должен получать фильтр поиска")
    @Test
    @WithMockUser(username = "user")
    void shouldTakeFilter() throws Exception {
        mockMvc.perform(post("/books/filtered")
                .param("filterText", "Bloh")
                .param("type", "authors")
                .flashAttr("filter", new Filter()))
                .andDo(print())
                .andExpect(view().name("book/list"))
                .andExpect(status().isOk())
                .andReturn();
    }
}
