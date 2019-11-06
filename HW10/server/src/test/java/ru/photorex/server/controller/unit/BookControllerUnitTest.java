package ru.photorex.server.controller.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.photorex.server.controller.BookController;
import ru.photorex.server.service.BookService;
import ru.photorex.server.to.AuthorTo;
import ru.photorex.server.to.BookTo;
import ru.photorex.server.to.GenreTo;

import java.util.List;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Котроллер для работы с книгами ")
@WebMvcTest(controllers = {BookController.class})
public class BookControllerUnitTest {

    private static final BookTo TO = new BookTo(null, "title", null, Set.of(new GenreTo("genre")), Set.of(new AuthorTo("fullName")), null);
    private static final BookTo TO_WITH_ID = new BookTo("1", "title", null, Set.of(new GenreTo("genre")), Set.of(new AuthorTo("fullName")), null);


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    BookService bookService;

    @DisplayName(" должен вернуть статус 200 на запрос getAll")
    @Test
    void shouldReturnStatusCodeOkInGetAllMethod() throws Exception {
        mockMvc.perform(get("/books").param("page", "0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @DisplayName(" должен вернуть статус 200 на запрос getAllFiltered")
    @Test
    void shouldReturnStatusCodeOkInGetAllFilteredMethod() throws Exception {
        given(bookService.getFilteredBook("Genre#1", "genre"))
                .willReturn(new CollectionModel<>(List.of(new BookTo())));
        mockMvc.perform(get("/books").param("search", "Genre#1").param("type", "genre"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @DisplayName(" должен вернуть статус 200 на запрос getById")
    @Test
    void shouldReturnStatusCodeOkInGetByIdMethod() throws Exception {
        mockMvc.perform(get("/books/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @DisplayName(" должен вернуть статус 201 на post запрос")
    @Test
    void shouldReturnStatusCodeCreatedInPostMethod() throws Exception {
        TO_WITH_ID.add(linkTo(methodOn(BookController.class).getById("1")).withSelfRel());
        given(bookService.saveBook(TO)).willReturn(TO_WITH_ID);
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(TO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
    }

    @DisplayName(" должен вернуть статус 200 на put запрос")
    @Test
    void shouldReturnStatusCodeOkInPutMethod() throws Exception {
        mockMvc.perform(put("/books/{id}", TO_WITH_ID.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(TO_WITH_ID)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @DisplayName(" должен вернуть статус 204 на delete запрос")
    @Test
    void shouldReturnStatusCodenoContentInDeleteMethod() throws Exception {
        mockMvc.perform(delete("/books/{id}", TO_WITH_ID.getId()))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
