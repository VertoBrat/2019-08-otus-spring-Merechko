package ru.photorex.hw9.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.photorex.hw9.service.LibraryWormAuthorService;
import ru.photorex.hw9.to.AuthorTo;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final LibraryWormAuthorService wormAuthorService;

    @GetMapping("/authors")
    public String getAllAuthors(Model model) {
        Set<AuthorTo> authorTos = wormAuthorService.findAllAuthors();
        model.addAttribute("authorsTos", authorTos);
        return "author/list";
    }
}
