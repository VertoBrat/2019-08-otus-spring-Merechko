package ru.photorex.hw9.controller;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.web.servlet.MockMvc;
import ru.photorex.hw9.model.Book;
import ru.photorex.hw9.to.CommentTo;


import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер для работы с комментариями ")
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MongoOperations mongoOperations;

    @DisplayName(" должен возвращать страницу редактирования комментов")
    @Test
    void shouldReturnEditCommentPage() throws Exception {
        mockMvc.perform(get("/comments/edit")
                .param("bookId", "1"))
                .andDo(print())
                .andExpect(view().name("comment/edit"))
                .andExpect(content().string(containsString("Комментарий")))
                .andExpect(status().isOk())
                .andReturn();
    }

    @DisplayName(" должен принять новый коммент и перенаправить на страницу книги")
    @Test
    void shouldTakeNewCommetAndRedirectToBookPage() throws Exception {
        val book = mongoOperations.findAll(Book.class).get(0);
        mockMvc.perform(post("/comments")
                .param("id", "")
                .param("text", "text")
                .param("bookId", book.getId())
                .flashAttr("to", new CommentTo()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/books/" + book.getId()))
                .andReturn();
    }
}
