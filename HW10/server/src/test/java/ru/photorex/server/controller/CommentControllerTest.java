package ru.photorex.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.photorex.server.model.Book;
import ru.photorex.server.model.Comment;
import ru.photorex.server.to.CommentTo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер для работы с комментами")
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    ObjectMapper mapper;

    @DisplayName(" должен сохранять коммент")
    @Test
    void shouldSaveComment() throws Exception {
        String bookId = mongoOperations.findAll(Book.class).get(0).getId();
        CommentTo to = new CommentTo(null, "comment", null, bookId);
        mockMvc.perform(post("/comments")
                .content(mapper.writeValueAsString(to))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", is(notNullValue())))
                .andReturn();
    }

    @DisplayName(" должен удалять коммент по id")
    @Test
    void shouldDeleteCommentById() throws Exception {
        List<Comment> comments = mongoOperations.findAll(Comment.class);
        String commentId = comments.get(0).getId();
        int expectedListSize = comments.size() - 1;
        mockMvc.perform(delete("/comments/{id}", commentId))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
        int actualListSize = mongoOperations.findAll(Comment.class).size();
        assertThat(actualListSize).isEqualTo(expectedListSize);
    }
}
