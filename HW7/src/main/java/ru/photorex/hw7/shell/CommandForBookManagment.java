package ru.photorex.hw7.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.photorex.hw7.exception.NoDataWithThisIdException;
import ru.photorex.hw7.model.Author;
import ru.photorex.hw7.model.Book;
import ru.photorex.hw7.model.Genre;
import ru.photorex.hw7.service.IOService;
import ru.photorex.hw7.service.LibraryWormBookService;
import ru.photorex.hw7.service.LibraryWormGenreService;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class CommandForBookManagment extends LibraryCommands {

    private final LibraryWormBookService wormBookService;
    private final LibraryWormGenreService wormGenreService;
    private final ShellTableBuilder tableBuilder;
    private final IOService console;

    @ShellMethod(value = "Display all books into library.", key = {"tb", "t books"})
    public void displayBooks() {
        List<Book> books = wormBookService.getAllBooks();
        printTable(books, false);
    }

    @ShellMethod(value = "Display all books some author.", key = {"tba", "tb author"})
    public void displayBooksByAuthor(@ShellOption({"-i"}) Long authorId) {
        Author author = new Author(authorId);
        List<Book> books = wormBookService.getBooksByAuthor(author);
        if (!books.isEmpty()) {
            printTable(books, false);
        } else console.printString("No books of this author");
    }

    @ShellMethod(value = "Display book by id.", key = {"tbi", "tb book by id"})
    public void displayBookById(@ShellOption({"-i"}) Long id) {
        Book book = null;
        try {
            book = wormBookService.getBookById(id);
            printTable(Collections.singletonList(book), true);
        } catch (DataAccessException e) {
            console.printString("No book with id = " + id);
        }

    }

    @ShellMethod(value = "Insert into books table new book.", key = {"ib", "i book"})
    public String insertBook(@ShellOption({"-t"}) String title,
                             @ShellOption({"-g"}) Long genreId,
                             @ShellOption(value = {"-a"}, arity = 1, defaultValue = "1") long[] authors) {
        try {
            wormGenreService.getGenreById(genreId);
        } catch (DataAccessException ex) {
            return "No Genre with id = " + genreId;
        }
        Book book = new Book(null, title, new Genre(genreId, null));
        book.setAuthor(Arrays.stream(authors).mapToObj(Author::new).collect(Collectors.toList()));
        return wormBookService.saveBook(book) != null ? "Added" : "Some Problem";
    }

    @ShellMethod(value = "Update book into books table.", key = {"ub", "u book"})
    public String updateBook(@ShellOption({"-i"}) Long id,
                             @ShellOption({"-t"}) String title,
                             @ShellOption({"-g"}) Long genreId,
                             @ShellOption(value = {"-a"}, arity = 1, defaultValue = "0") long[] authors) {
        Genre genre = wormGenreService.getGenreById(genreId);
        if (genre == null)
            return "No Genre with id = " + genreId;
        Book book = new Book(id, title, genre);
        if (authors.length == 1 && authors[0] == 0) book.setAuthor(Collections.EMPTY_LIST);
        else book.setAuthor(Arrays.stream(authors).mapToObj(Author::new).collect(Collectors.toList()));
        try {
            wormBookService.saveBook(book);
        } catch (NoDataWithThisIdException e) {
            return e.getLocalizedMessage();
        }
        return "Updated";
    }

    @ShellMethod(value = "Delete book from table using id.", key = {"db", "d book"})
    public String deleteBook(@ShellOption({"-i"}) Long id) {
        if (wormBookService.deleteBook(id))
            return "Successful";
        return "Some problem";
    }

    private void printTable(List<Book> books, boolean withAuthors) {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Id");
        headers.put("title", "Title");
        headers.put("genre", "Genre");
        if (withAuthors)
            headers.put("author", "Authors");
        tableBuilder.build(books, headers);
    }
}
