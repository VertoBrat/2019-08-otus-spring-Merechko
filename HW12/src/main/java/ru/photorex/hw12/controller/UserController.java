package ru.photorex.hw12.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.photorex.hw12.service.LibraryWormUserService;
import ru.photorex.hw12.to.RegUser;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final LibraryWormUserService userService;

    @GetMapping("/users/new")
    public String registrationPage(Model model) {
        logger.info("registrationPage");
        RegUser regUser = new RegUser();
        model.addAttribute("regUser", regUser);
        return "user/new";
    }

    @PostMapping("/users/registration")
    public String booksPageAfterRegistration(@Valid @ModelAttribute RegUser user, BindingResult result) {
        logger.info("booksPageAfterRegistration with user {}", user);
        if (result.hasErrors()) {
            return "user/new";
        }
        userService.saveNewUser(user);
        return "redirect:/login";
    }
}
