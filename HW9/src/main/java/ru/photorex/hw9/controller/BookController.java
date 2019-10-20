package ru.photorex.hw9.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.photorex.hw9.model.Author;
import ru.photorex.hw9.model.Book;
import ru.photorex.hw9.service.LibraryWormAuthorService;
import ru.photorex.hw9.service.LibraryWormBookService;
import ru.photorex.hw9.service.LibraryWormGenreService;

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
        List<Book> books = wormBookService.findAllBooks();
        Set<Author> authors = wormAuthorService.findAllAuthors();
        Set<String> genres = wormGenreService.findAllGenres();
        model.addAttribute("booksSize", books.size());
        model.addAttribute("authorsSize", authors.size());
        model.addAttribute("genresSize", genres.size());
        return "index";
    }

    @GetMapping("/books")
    public String getAllBooks(Model model) {
        List<Book> books = wormBookService.findAllBooks();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable("id") String id) {
        wormBookService.deleteBook(id);
        return "redirect:/books";
    }
}
