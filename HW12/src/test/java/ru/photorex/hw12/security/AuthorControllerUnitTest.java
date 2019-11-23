package ru.photorex.hw12.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.photorex.hw12.controller.AuthorController;
import ru.photorex.hw12.service.LibraryWormAuthorService;
import ru.photorex.hw12.to.AuthorTo;

import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DisplayName("SecurityConfig для работы с авторами ")
@WebMvcTest(controllers = {AuthorController.class})
@Import({LoggingAccessDeniedHandler.class})
public class AuthorControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LibraryWormAuthorService authorService;

    @DisplayName(" должен возвращать страницу авторов и сет авторов для любого user")
    @Test
    @WithAnonymousUser
    void shouldReturnAuthorListViewWithAuthors() throws Exception {
        given(authorService.findAllAuthors()).willReturn(Set.of(new AuthorTo()));

        mockMvc.perform(get("/authors"))
                .andDo(print())
                .andExpect(unauthenticated())
                .andExpect(status().isOk())
                .andExpect(view().name("author/list"))
                .andReturn();
    }
}
