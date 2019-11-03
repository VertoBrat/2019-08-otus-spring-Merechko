package ru.photorex.server.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.photorex.server.service.AuthorService;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;
    private final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    @GetMapping("/authors")
    public ResponseEntity getAll() {
        logger.info("getAll");
        return ResponseEntity.ok(authorService.findAllAuthors());
    }
}
