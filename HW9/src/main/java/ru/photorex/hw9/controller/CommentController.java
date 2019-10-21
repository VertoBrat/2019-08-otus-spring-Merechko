package ru.photorex.hw9.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.photorex.hw9.service.LibraryWormCommentService;
import ru.photorex.hw9.to.CommentTo;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final LibraryWormCommentService wormCommentService;

    @GetMapping("/comments/edit")
    public String getEditForm(@RequestParam(value = "id", required = false) String id,
                              @RequestParam(value = "bookId", required = false) String bookId,
                              Model model) {
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
    public String updateSaveComment(@Valid @ModelAttribute CommentTo to, BindingResult result) {
        if (result.hasErrors()){
            return "comment/edit";
        }
        if (to.getId().isEmpty()) {
            wormCommentService.saveComment(to.getBookId(), to.getText());
        } else {
            wormCommentService.updateComment(to.getId(), to.getText());
        }
        return "redirect:/books/" + to.getBookId();
    }
}
