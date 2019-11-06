package ru.photorex.server.controller.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.photorex.server.controller.CommentController;
import ru.photorex.server.service.CommentService;
import ru.photorex.server.to.CommentTo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контролле для рабоыт с комментами ")
@WebMvcTest(controllers = {CommentController.class})
public class CommentControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    CommentService commentService;

    @DisplayName(" должен возвращать статус 201")
    @Test
    void shouldReturnStatusCodeCreated() throws Exception {
        CommentTo to = new CommentTo(null, "test", null, "1");
        mockMvc.perform(post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(to)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
    }

    @DisplayName(" должен возвращать статус 204")
    @Test
    void shouldReturnStatusCodeNoContent() throws Exception {
        mockMvc.perform(delete("/comments/{id}", "1"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
