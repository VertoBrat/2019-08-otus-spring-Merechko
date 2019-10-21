package ru.photorex.hw9.controller;

import lombok.RequiredArgsConstructor;
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

    @GetMapping("/")
    public String indexPage(Model model) {
        List<BookTo> books = wormBookService.findAllBooks();
        Set<AuthorTo> authors = wormAuthorService.findAllAuthors();
        Set<GenreTo> genres = wormGenreService.findAllGenres();
        model.addAttribute("booksSize", books.size());
        model.addAttribute("authorsSize", authors.size());
        model.addAttribute("genresSize", genres.size());
        return "index";
    }

    @GetMapping("/books")
    public String getAllBooks(Model model) {
        List<BookTo> books = wormBookService.findAllBooks();
        Filter filter = new Filter();
        model.addAttribute("books", books);
        model.addAttribute("filter", filter);
        return "book/list";
    }

    @GetMapping("/books/{id}")
    public String getBook(@PathVariable("id") String id, Model model) {
        BookTo bookTo = wormBookService.findBookById(id);
        model.addAttribute("bookTo", bookTo);
        return "book/view";
    }

    @GetMapping("/books/edit/{id}")
    public String editBookPage(@PathVariable("id") String id, Model model) {
        BookTo bookTo;
        if (id.equals("new")) {
            bookTo = new BookTo();
        } else bookTo = wormBookService.findBookById(id);
        model.addAttribute("bookTo", bookTo);
        return "book/edit";
    }

    @PostMapping("/books")
    public String updateSaveBook(@Valid @ModelAttribute BookTo bookTo, BindingResult result) {
        if (result.hasErrors()) {
            return "book/edit";
        }
        wormBookService.updateSaveBook(bookTo);
        return "redirect:/books";
    }

    @PostMapping("/books/filtered")
    public String filteredBooks(@ModelAttribute Filter filter, Model model) {
        List<BookTo> books = wormBookService.filteredBooks(filter);
        model.addAttribute("books", books);
        return "book/list";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable("id") String id) {
        wormBookService.deleteBook(id);
        return "redirect:/books";
    }
}
