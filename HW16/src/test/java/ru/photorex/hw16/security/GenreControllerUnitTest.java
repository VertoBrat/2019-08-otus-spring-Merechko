package ru.photorex.hw16.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import ru.photorex.hw16.controller.GenreController;
import ru.photorex.hw16.service.LibraryWormGenreService;
import ru.photorex.hw16.to.GenreTo;

import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DisplayName("SecurityConfig для работы с жанрами ")
@WebMvcTest(controllers = {GenreController.class})
@Import({LoggingAccessDeniedHandler.class})
public class GenreControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LibraryWormGenreService genreService;

    @DisplayName(" должен вернуть страницу с жанрами и списком жанров любому user")
    @Test
    void shouldReturnGenreViewWithSetOfGenres() throws Exception {
        given(genreService.findAllGenres()).willReturn(Set.of(new GenreTo()));

        mockMvc.perform(get("/genres"))
                .andDo(print())
                .andExpect(unauthenticated())
                .andExpect(status().isOk())
                .andExpect(view().name("genre/list"))
                .andReturn();
    }
}
