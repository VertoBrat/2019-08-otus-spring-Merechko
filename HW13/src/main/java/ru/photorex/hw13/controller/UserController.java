package ru.photorex.hw13.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.photorex.hw13.service.LibraryWormUserService;
import ru.photorex.hw13.to.RegUser;

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
    public String booksPageAfterRegistration(@Valid @ModelAttribute RegUser user, Model model, BindingResult result) {
        logger.info("booksPageAfterRegistration with user {} and errors {}", user, result.getErrorCount());
        if (result.hasErrors()) {
            return "user/new";
        }
        if (userService.findUserByUserName(user.getUsername()).getNick() != null) {
            model.addAttribute("error", "error");
            return "user/new";
        }
        model.addAttribute("success", "success");
        userService.saveNewUser(user);
        return "redirect:/login";
    }
}
