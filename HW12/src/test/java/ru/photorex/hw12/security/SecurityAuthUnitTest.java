package ru.photorex.hw12.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import ru.photorex.hw12.controller.AuthorController;
import ru.photorex.hw12.controller.GenreController;
import ru.photorex.hw12.controller.UserController;
import ru.photorex.hw12.service.LibraryWormAuthorService;
import ru.photorex.hw12.service.LibraryWormGenreService;
import ru.photorex.hw12.service.LibraryWormUserService;
import ru.photorex.hw12.to.AuthorTo;
import ru.photorex.hw12.to.GenreTo;

import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Класс, проверяющий auth ")
@WebMvcTest(controllers = {AuthorController.class, UserController.class, GenreController.class})
@Import(LoggingAccessDeniedHandler.class)
public class SecurityAuthUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LibraryWormAuthorService authorService;

    @MockBean
    LibraryWormGenreService genreService;

    @MockBean
    LibraryWormUserService userService;

    @DisplayName(" должен проверять доступ для страниц без аутентификации")
    @ParameterizedTest
    @ValueSource(strings = {"/authors", "/genres", "/users/new"})
    void shouldCheckAccessWithoutAuth(String url) throws Exception {
        given(authorService.findAllAuthors()).willReturn(Set.of(new AuthorTo()));
        given(genreService.findAllGenres()).willReturn(Set.of(new GenreTo()));

        mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(unauthenticated())
                .andExpect(status().isOk())
                .andReturn();
    }
}
