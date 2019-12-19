package ru.photorex.hw16.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.photorex.hw16.service.LibraryWormAuthorService;
import ru.photorex.hw16.to.AuthorTo;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final LibraryWormAuthorService wormAuthorService;
    private final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    @GetMapping("/authors")
    public String getAuthorListPage(Model model) {
        logger.info("getAuthorListPage");
        Set<AuthorTo> authorTos = wormAuthorService.findAllAuthors();
        model.addAttribute("authorsTos", authorTos);
        return "author/list";
    }
}
