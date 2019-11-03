package ru.photorex.server.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.photorex.server.service.GenreService;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;
    private final Logger logger = LoggerFactory.getLogger(GenreController.class);

    @GetMapping("/genres")
    public ResponseEntity getGenreListPage() {
        logger.info("getGenreListPage");
        return ResponseEntity.ok(genreService.findAllGenres());
    }
}
