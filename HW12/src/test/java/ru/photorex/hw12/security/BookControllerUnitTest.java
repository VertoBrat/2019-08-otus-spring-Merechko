package ru.photorex.hw12.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.photorex.hw12.controller.BookController;
import ru.photorex.hw12.service.LibraryWormAuthorService;
import ru.photorex.hw12.service.LibraryWormBookService;
import ru.photorex.hw12.service.LibraryWormGenreService;
import ru.photorex.hw12.to.BookTo;
import ru.photorex.hw12.to.mapper.UserMapper;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("SecurityConfig для работы с книгами ")
@WebMvcTest(controllers = {BookController.class})
@Import({LoggingAccessDeniedHandler.class})
public class BookControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LibraryWormBookService wormBookService;

    @MockBean
    LibraryWormAuthorService wormAuthorService;

    @MockBean
    LibraryWormGenreService wormGenreService;

    @MockBean
    UserMapper mapper;

    @DisplayName(" должен запретить доступ не аутентифицированным пользователям")
    @Test
    void shouldAccessOnlyAuthUsers() throws Exception {
        mockMvc.perform(get("/books"))
                .andDo(print())
                .andExpect(unauthenticated());
    }

    @DisplayName(" должен разрешать доступ на изменение книги только админам")
    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void shouldAccessEditBookOnlyForAdminUser() throws Exception {
        given(wormBookService.findBookById("1")).willReturn(new BookTo());

        mockMvc.perform(get("/books/edit/{id}", "1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(view().name("book/edit"))
                .andReturn();
    }

    @DisplayName(" должен разрешать доступ на добавление книги только админу")
    @Test
    @WithMockUser
    void shouldAccessAddNewBookOnlyForAdmin() throws Exception {
        mockMvc.perform(get("/books/edit"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/access-denied"))
                .andReturn();
    }

    @DisplayName(" должен разрешать доступ на удаление книги только админу")
    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void shouldAccessDeleteBookOnlyForAdmin() throws Exception {
        mockMvc.perform(post("/books/delete").param("id", "1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(view().name("redirect:/books"))
                .andReturn();
    }
}
