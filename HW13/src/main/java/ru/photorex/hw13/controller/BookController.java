package ru.photorex.hw13.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.photorex.hw13.model.User;
import ru.photorex.hw13.service.LibraryWormBookService;
import ru.photorex.hw13.to.*;
import ru.photorex.hw13.to.mapper.UserMapper;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private static final String BOOK_TO_MODEL_ATTRIBUTE = "bookTo";
    private final LibraryWormBookService wormBookService;
    private final UserMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    @GetMapping("/")
    public String indexPage(Model model) {
        logger.info("indexPage");
        return "index";
    }

    @GetMapping("/books")
    public String getBookListPage(Model model) {
        logger.info("getBookListPage");
        List<BookTo> books = wormBookService.findAllBooks();
        Filter filter = new Filter();
        model.addAttribute("books", books);
        model.addAttribute("filter", filter);
        return "book/list";
    }

    @GetMapping("/books/{id}")
    public String getBookViewPage(@AuthenticationPrincipal User user, @PathVariable("id") String id, Model model) {
        logger.info("getBookViewPage {}", id);
        BookTo bookTo = wormBookService.findBookById(id);
        UserTo userTo = mapper.toTo(user);
        model.addAttribute(BOOK_TO_MODEL_ATTRIBUTE, bookTo);
        model.addAttribute("userId", userTo.getId());
        return "book/view";
    }

    @GetMapping("/books/edit/{id}")
    public String getBookEditPage(@PathVariable("id") String id, Model model) {
        logger.info("getBookEditPage {}", id);
        BookTo bookTo = wormBookService.findBookByIdForEdit(id);
        model.addAttribute(BOOK_TO_MODEL_ATTRIBUTE, bookTo);
        return "book/edit";
    }

    @GetMapping("/books/edit")
    @PreAuthorize("hasRole('ROLE_EDITOR')")
    public String getBookEditBlankPage(Model model) {
        logger.info("getBookEditBlankPage");
        BookTo bookTo = new BookTo();
        model.addAttribute(BOOK_TO_MODEL_ATTRIBUTE, bookTo);
        return "book/edit";
    }

    @PostMapping("/books")
    public String updateSaveBook(@Valid @ModelAttribute BookTo bookTo, BindingResult result) {
        logger.info("updateSaveBook {} with {} errors", bookTo, result.getErrorCount());
        if (result.hasErrors()) {
            return "book/edit";
        }
        wormBookService.updateSaveBook(bookTo);
        return "redirect:/books";
    }

    @PostMapping("/books/filtered")
    public String getFilteredBookList(@ModelAttribute Filter filter, Model model) {
        logger.info("getFilteredBookList with filter type {} and filter text {}", filter.getType(), filter.getFilterText());
        List<BookTo> books = wormBookService.filteredBooks(filter);
        model.addAttribute("books", books);
        return "book/list";
    }

    @PostMapping("/books/delete")
    public String deleteBook(String id) {
        logger.info("deleteBook {}", id);
        wormBookService.deleteBook(id);
        return "redirect:/books";
    }
}
