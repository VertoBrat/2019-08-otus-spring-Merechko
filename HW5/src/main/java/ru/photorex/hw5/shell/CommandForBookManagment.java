package ru.photorex.hw5.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.photorex.hw5.model.Author;
import ru.photorex.hw5.model.Book;
import ru.photorex.hw5.model.Genre;
import ru.photorex.hw5.service.IOService;
import ru.photorex.hw5.service.LibraryWormBookService;

import java.util.*;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class CommandForBookManagment implements Blocked {

    private final LibraryWormBookService wormBookService;
    private final ShellTableBuilder tableBuilder;
    private final IOService console;

    private boolean isLogged = false;

    @ShellMethod(value = "Display all books into library.", key = {"tb", "t books"})
    @ShellMethodAvailability("availabilityCheck")
    public void displayBooks() {
        List<Book> books = wormBookService.getAllBooks();
        printTable(books);
    }

    @ShellMethod(value = "Display all books some author.", key = {"tba", "tb author"})
    @ShellMethodAvailability("availabilityCheck")
    public void displayBooksByAuthor(@ShellOption({"-i"}) Long authorId) {
        Author author = new Author();
        author.setId(authorId);
        List<Book> books = wormBookService.getBooksByAuthor(author);
        if (!books.isEmpty()) {
            printTable(books);
        } else console.printString("No books of this author");
    }

    @ShellMethod(value = "Display book by id.", key = {"tbi", "tb book by id"})
    @ShellMethodAvailability("availabilityCheck")
    public void displayBookById(@ShellOption({"-i"}) Long id) {
        Book book = null;
        try {
            book = wormBookService.getBookById(id);
            printTable(Collections.singletonList(book));
        } catch (DataAccessException e) {
            console.printString("No book with id = " + id);
        }

    }

    @ShellMethod(value = "Insert into books table new book.", key = {"ib", "i book"})
    @ShellMethodAvailability("availabilityCheck")
    public String insertBook(@ShellOption({"-t"}) String title,
                             @ShellOption({"-g"}) Long genreId,
                             @ShellOption(value = {"-a"}, arity = 1, defaultValue = "1") long[] authors) {
        Book book = new Book();
        book.setTitle(title);
        book.setGenre(new Genre(genreId));
        book.setAuthor(Arrays.stream(authors).mapToObj(Author::new).collect(Collectors.toSet()));
        return wormBookService.saveBook(book) != null ? "Added" : "Some Problem";
    }

    @ShellMethod(value = "Update book into books table.", key = {"ub", "u book"})
    @ShellMethodAvailability("availabilityCheck")
    public String updateBook(@ShellOption({"-i"}) Long id,
                             @ShellOption({"-t"}) String title,
                             @ShellOption({"-g"}) Long genreId,
                             @ShellOption(value = {"-a"}, arity = 1, defaultValue = "0") long[] authors) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setGenre(new Genre(genreId));
        if (authors.length == 1 && authors[0] == 0) book.setAuthor(Collections.EMPTY_SET);
        else book.setAuthor(Arrays.stream(authors).mapToObj(Author::new).collect(Collectors.toSet()));
        return wormBookService.saveBook(book).toString();
    }

    @ShellMethod(value = "Delete book from table using id.", key = {"db", "d book"})
    @ShellMethodAvailability("availabilityCheck")
    public String deleteBook(@ShellOption({"-i"}) Long id) {
        if (wormBookService.deleteBook(id))
            return "Successful";
        return "Some problem";
    }

    private void printTable(List<Book> books) {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Id");
        headers.put("title", "Title");
        headers.put("genre", "Genre");
        tableBuilder.build(books, headers);
    }

    @Override
    public void unBlock() {
        this.isLogged = true;
    }

    @Override
    public boolean isLogged() {
        return isLogged;
    }
}
