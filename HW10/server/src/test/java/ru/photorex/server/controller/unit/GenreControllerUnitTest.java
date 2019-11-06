package ru.photorex.server.controller.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.photorex.server.controller.GenreController;
import ru.photorex.server.service.GenreService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер для работы с жанрами ")
@WebMvcTest(controllers = {GenreController.class})
public class GenreControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    GenreService genreService;

    @DisplayName(" должен возвращать статус 200")
    @Test
    void shouldReturnStatusCodeOk() throws Exception {
        mockMvc.perform(get("/genres"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
