package ru.photorex.hw12.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.photorex.hw12.model.User;
import ru.photorex.hw12.service.LibraryWormCommentService;
import ru.photorex.hw12.to.CommentTo;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final LibraryWormCommentService wormCommentService;
    private final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @GetMapping("/comments/edit")
    public String getCommentEditPage(@RequestParam(value = "id", required = false) String id,
                                     @RequestParam(value = "bookId") String bookId,
                                     Model model) {
        logger.info("getCommentEditPage with comment id = {}, bookId = {}", id, bookId);
        if (id != null) {
            CommentTo commentTo = wormCommentService.findCommentById(id);
            model.addAttribute("commentTo", commentTo);
        } else {
            CommentTo commentTo = new CommentTo();
            commentTo.setBookId(bookId);
            model.addAttribute("commentTo", commentTo);
        }
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
}
