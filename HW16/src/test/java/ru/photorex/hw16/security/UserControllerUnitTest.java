package ru.photorex.hw16.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import ru.photorex.hw16.controller.UserController;
import ru.photorex.hw16.service.LibraryWormUserService;
import ru.photorex.hw16.to.RegUser;
import ru.photorex.hw16.to.UserTo;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DisplayName("SecurityConfig для аутентификации ")
@WebMvcTest(controllers = {UserController.class})
@Import({LoggingAccessDeniedHandler.class})
public class UserControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LibraryWormUserService userService;

    @DisplayName(" должен разрешать доступ на страницу логина всем")
    @Test
    void shouldAccessLoginPageForAllUsers() throws Exception {
        mockMvc.perform(get("/users/new"))
                .andDo(print())
                .andExpect(unauthenticated())
                .andExpect(status().isOk())
                .andExpect(view().name("user/new"))
                .andReturn();
    }

    @DisplayName(" должен разрешать регистрацию любому пользователю")
    @Test
    void shouldAccessRegistrationForAnyUser() throws Exception {
        given(userService.findUserByUserName("nick")).willReturn(new UserTo());

        mockMvc.perform(post("/users/registration")
                .param("username", "nick")
                .param("password", "12345")
                .param("fullName", "Dima")
                .flashAttr("user", new RegUser()))
                .andDo(print())
                .andExpect(unauthenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"))
                .andReturn();
    }
}
