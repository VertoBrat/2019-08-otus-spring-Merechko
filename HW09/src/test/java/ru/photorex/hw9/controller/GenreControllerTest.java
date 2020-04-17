package ru.photorex.hw9.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер для работы с комментариями ")
@SpringBootTest
@AutoConfigureMockMvc
public class GenreControllerTest {

    private static final String GENRE_PAGE_STRING = "Название жанра";

    @Autowired
    MockMvc mockMvc;

    @DisplayName(" должен возвращать страницу списка жанров")
    @Test
    void shouldReturnGenreListPage() throws Exception {
        mockMvc.perform(get("/genres"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("genre/list"))
                .andExpect(content().string(containsString(GENRE_PAGE_STRING)))
                .andReturn();
    }
}
