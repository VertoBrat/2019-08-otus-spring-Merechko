package ru.photorex.hw12.controller;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.photorex.hw12.model.Book;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер для работы с login, error")
@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MongoOperations mongoOperations;

    @DisplayName(" должен возвращать страницу login")
    @Test
    void shouldReturnLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andReturn();
    }

    @DisplayName(" должен направлять на страницу access-denied")
    @Test
    @WithMockUser
    void shouldReturnAccessDeniedPage() throws Exception {
        val book = mongoOperations.findAll(Book.class).get(0);
        mockMvc.perform(get("/books/edit/" + book.getId()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("location", "/access-denied"))
                .andReturn();
    }

    @DisplayName(" должен направлять на страницу error")
    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void shouldReturnErrorPage() throws Exception {
        mockMvc.perform(get("/books/edit/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andReturn();
    }
}
