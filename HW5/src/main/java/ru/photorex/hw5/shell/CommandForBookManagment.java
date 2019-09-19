package ru.photorex.hw5.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.photorex.hw5.model.Author;
import ru.photorex.hw5.model.Book;
import ru.photorex.hw5.service.LibraryWormBookService;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class CommandForBookManagment {

    private final LibraryWormBookService wormBookService;
    private final ShellTableBuilder tableBuilder;

    @ShellMethod(value = "Display all books into library", key = {"tb", "t books"})
    public void displayBooks() {
        List<Book> books = wormBookService.getAllBooks();
        printTable(books);
    }

    @ShellMethod(value = "Display all books some author", key = {"tba", "tb author"})
    public void displayBooksByAuthor(@ShellOption({"-i"}) Long authorId) {
        Author author = new Author();
        author.setId(authorId);
        List<Book> books = wormBookService.getBooksByAuthor(author);
        printTable(books);
    }

    @ShellMethod(value = "Display book by id", key = {"tbi", "tb id"})
    public void displayBookById(@ShellOption({"-i"}) Long id) {
        Book book = wormBookService.getBookById(id);
        printTable(Collections.singletonList(book));
    }

    private void printTable(List<Book> books) {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Id");
        headers.put("title", "Title");
        headers.put("genre", "Genre");
        tableBuilder.build(books, headers);
    }
}
