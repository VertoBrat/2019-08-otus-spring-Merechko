package ru.photorex.hw9.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.photorex.hw9.service.LibraryWormGenreService;
import ru.photorex.hw9.to.GenreTo;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class GenreController {

    private final LibraryWormGenreService wormGenreService;

    @GetMapping("/genres")
    public String getAllGenres(Model model) {
        Set<GenreTo> genreTos = wormGenreService.findAllGenres();
        model.addAttribute("genreTos", genreTos);
        return "genre/list";
    }
}
