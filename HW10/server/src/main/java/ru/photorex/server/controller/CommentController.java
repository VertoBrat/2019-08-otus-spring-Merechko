package ru.photorex.server.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.photorex.server.service.CommentService;
import ru.photorex.server.to.CommentTo;

import javax.validation.Valid;

import static ru.photorex.server.utils.DataValidation.checkErrors;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @PostMapping("/comments")
    public ResponseEntity save(@Valid @RequestBody CommentTo to, BindingResult result) {
        logger.info("save {} with {} errors", to, result.getErrorCount());
        checkErrors(result);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.saveComment(to.getBookId(), to.getText()));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        logger.info("delete with id {}", id);
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
