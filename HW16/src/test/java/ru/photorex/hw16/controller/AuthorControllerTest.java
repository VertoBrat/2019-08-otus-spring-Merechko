package ru.photorex.hw16.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер для работы с авторами ")
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest {

    private static final String AUTHORS_LIST_STRING = "ФИО автора";

    @Autowired
    MockMvc mockMvc;

    @DisplayName(" должен возвращать всех авторов")
    @Test
    void shouldReturnAuthorsPage() throws Exception {
        mockMvc.perform(get("/authors"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("author/list"))
                .andExpect(content().string(containsString(AUTHORS_LIST_STRING)))
                .andReturn();
    }
}
