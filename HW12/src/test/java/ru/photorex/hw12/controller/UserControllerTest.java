package ru.photorex.hw12.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.photorex.hw12.to.RegUser;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер для работы с юзерами ")
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @DisplayName(" должен возвращать страницу для регистрации")
    @Test
    void shouldReturnRegistrationPage() throws Exception {
        mockMvc.perform(get("/users/new"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/new"))
                .andReturn();
    }

    @DisplayName(" должен записывать пользователя и направлять на страницу login")
    @Test
    void shouldSaveUserAndReturnLoginPage() throws Exception {
        mockMvc.perform(post("/users/registration")
                .param("username", "username")
                .param("password", "password")
                .param("fullName", "fullName")
                .flashAttr("user", new RegUser()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"))
                .andReturn();
    }
}
