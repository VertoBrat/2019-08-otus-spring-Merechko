package ru.photorex.hw13.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;
import ru.photorex.hw13.model.Book;
import ru.photorex.hw13.model.Comment;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Класс, проверяющий auth ")
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityAclTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MongoOperations mongoOperations;

    @Test
    @DisplayName(" должен получать одну книгу для user")
    @WithMockUser
    void shouldCheckAccessForUserAndReturnOneBook() throws Exception {
        mockMvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", hasSize(1)))
                .andReturn();
    }

    @Test
    @DisplayName(" должен получать три книги для admin")
    @WithMockUser(username = "admin")
    void shouldCheckAccessForAdminAndReturnThreeBooks() throws Exception {
        mockMvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", hasSize(3)))
                .andReturn();
    }

    @Test
    @DisplayName(" должен запрещать доступ к обьекту редактирования и удаления для user")
    @WithMockUser
    void shouldAccessDeniedToEditAndDeleteBook() throws Exception {
        String bookId = mongoOperations.findAll(Book.class).get(0).getId();
        mockMvc.perform(get("/books/edit/{id}", bookId))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/access-denied"))
                .andReturn();

        mockMvc.perform(post("/books/delete")
                .param("id", bookId))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/access-denied"))
                .andReturn();
    }

    @Test
    @DisplayName(" должен разрешать доступ к обьекту редактирования и запрешать к обьекту удаления для admin")
    @WithMockUser(username = "admin")
    void shouldAccessToEditAndDeleteBook() throws Exception {
        String bookId = mongoOperations.findAll(Book.class).get(0).getId();
        mockMvc.perform(get("/books/edit/{id}", bookId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("book/edit"))
                .andReturn();

        mockMvc.perform(post("/books/delete")
                .param("id", bookId))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/access-denied"))
                .andReturn();
    }

    @Test
    @DisplayName(" должен разрешать редактировать и удалять только свои комментарии")
    @WithMockUser
    void shouldAccessEditAndDeleteNotAllComments() throws Exception {
        List<Comment> comments = mongoOperations.findAll(Comment.class);
        Comment userComment = comments.stream()
                .filter(c -> c.getUser().getUsername().equals("user"))
                .findAny()
                .orElse(null);
        Comment adminComment = comments.stream()
                .filter(c -> c.getUser().getUsername().equals("admin"))
                .findAny()
                .orElse(null);
        String commentId = userComment.getId();
        String adminCommentId = adminComment.getId();
        mockMvc.perform(get("/comments/edit/{id}", commentId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("comment/edit"))
                .andReturn();

        mockMvc.perform(post("/comments/delete")
                .param("commentId", commentId))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books/" + userComment.getBook().getId()))
                .andReturn();

        mockMvc.perform(post("/comments/delete")
                .param("commentId", adminCommentId))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/access-denied"))
                .andReturn();
    }

}
