package ru.photorex.hw16.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.photorex.hw16.model.User;
import ru.photorex.hw16.service.LibraryWormCommentService;
import ru.photorex.hw16.to.CommentTo;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final LibraryWormCommentService wormCommentService;
    private final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @GetMapping("/comments/edit")
    public String getCommentEditBlankPage(@RequestParam(value = "bookId") String bookId,
                                          Model model) {
        logger.info("getCommentEditBlankPage with bookId = {}", bookId);
        CommentTo commentTo = new CommentTo();
        commentTo.setBookId(bookId);
        model.addAttribute("commentTo", commentTo);
        return "comment/edit";
    }

    @GetMapping("/comments/edit/{id}")
    public String getCommentEditPage(@PathVariable("id") String commentId, Model model) {
        logger.info("getCommentEditPage with id {}", commentId);
        CommentTo commentTo = wormCommentService.findCommentById(commentId);
        model.addAttribute("commentTo", commentTo);
        return "comment/edit";
    }

    @PostMapping("/comments")
    public String updateSaveComment(@AuthenticationPrincipal User user, @Valid @ModelAttribute CommentTo to, BindingResult result) {
        logger.info("updateSaveComment {} with {} errors", to, result.getErrorCount());
        if (result.hasErrors()) {
            return "comment/edit";
        }
        if (to.getId().isEmpty()) {
            wormCommentService.saveComment(to.getBookId(), to.getText(), user);
        } else {
            wormCommentService.updateComment(to.getId(), to.getText());
        }
        return "redirect:/books/" + to.getBookId();
    }

    @PostMapping("/comments/delete")
    public String deleteComment(String commentId) {
        logger.info("deleteComment with id {}", commentId);
        CommentTo to = wormCommentService.findCommentById(commentId);
        wormCommentService.deleteComment(commentId);
        return "redirect:/books/" + to.getBookId();
    }
}
