package ru.photorex.hw9.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.photorex.hw9.service.LibraryWormAuthorService;
import ru.photorex.hw9.service.LibraryWormBookService;
import ru.photorex.hw9.service.LibraryWormGenreService;
import ru.photorex.hw9.to.AuthorTo;
import ru.photorex.hw9.to.BookTo;
import ru.photorex.hw9.to.Filter;
import ru.photorex.hw9.to.GenreTo;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final LibraryWormBookService wormBookService;
    private final LibraryWormAuthorService wormAuthorService;
    private final LibraryWormGenreService wormGenreService;
    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    @GetMapping("/")
    public String indexPage(Model model) {
        logger.info("indexPage");
        List<BookTo> books = wormBookService.findAllBooks();
        Set<AuthorTo> authors = wormAuthorService.findAllAuthors();
        Set<GenreTo> genres = wormGenreService.findAllGenres();
        model.addAttribute("booksSize", books.size());
        model.addAttribute("authorsSize", authors.size());
        model.addAttribute("genresSize", genres.size());
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
    public String getBookViewPage(@PathVariable("id") String id, Model model) {
        logger.info("getBookViewPage {}", id);
        BookTo bookTo = wormBookService.findBookById(id);
        model.addAttribute("bookTo", bookTo);
        return "book/view";
    }

    @GetMapping("/books/edit/{id}")
    public String getBookEditPage(@PathVariable("id") String id, Model model) {
        logger.info("getBookEditPage {}", id);
        BookTo bookTo = wormBookService.findBookById(id);
        model.addAttribute("bookTo", bookTo);
        return "book/edit";
    }

    @GetMapping("/books/edit")
    public String getBookEditBlankPage(Model model) {
        logger.info("getBookEditBlankPage");
        BookTo bookTo = new BookTo();
        model.addAttribute("bookTo", bookTo);
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

    @GetMapping("/books/filtered")
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
