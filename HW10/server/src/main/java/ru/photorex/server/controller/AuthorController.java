package ru.photorex.server.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.photorex.server.service.AuthorService;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;
    private final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    @GetMapping("/authors")
    public ResponseEntity getAll(Model model) {
        logger.info("getAll");
        return ResponseEntity.ok(authorService.findAllAuthors());
    }
}
