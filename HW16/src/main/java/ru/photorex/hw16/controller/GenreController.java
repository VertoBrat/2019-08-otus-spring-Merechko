package ru.photorex.hw16.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.photorex.hw16.service.LibraryWormGenreService;
import ru.photorex.hw16.to.GenreTo;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class GenreController {

    private final LibraryWormGenreService wormGenreService;
    private final Logger logger = LoggerFactory.getLogger(GenreController.class);

    @GetMapping("/genres")
    public String getGenreListPage(Model model) {
        logger.info("getGenreListPage");
        Set<GenreTo> genreTos = wormGenreService.findAllGenres();
        model.addAttribute("genreTos", genreTos);
        return "genre/list";
    }
}
