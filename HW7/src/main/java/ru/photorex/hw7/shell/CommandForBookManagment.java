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
        try {
            List<Book> books = wormBookService.getBooksByAuthor(author);
            if (!books.isEmpty())
                printTable(books, false);
            else
                console.printString("No books this author");
        } catch (NoDataWithThisIdException ex) {
            console.printString(ex.getLocalizedMessage());
        }
    }

    @ShellMethod(value = "Display book by id.", key = {"tbi", "tb book by id"})
    public void displayBookById(@ShellOption({"-i"}) Long id) {
        Book book = wormBookService.getBookById(id);
        if (book == null)
            console.printString("There is no book with id = " + id);
        else
            printTable(Collections.singletonList(book), true);
    }

    /**
     * You can add two authors maximum!
     * If you want add only one author, use "0" for second author
     */
    @ShellMethod(value = "Insert into books table new book.", key = {"ib", "i book"})
    public String insertBook(@ShellOption({"-t"}) String title,
                             @ShellOption({"-g"}) Long genreId,
                             @ShellOption(value = {"-a"}, arity = 2, defaultValue = "1") long[] authors) {
        Book book = new Book(null, title, new Genre(genreId, null));
        book.setAuthor(Arrays.stream(authors).mapToObj(Author::new)
                .filter(a -> a.getId() != 0)
                .collect(Collectors.toList()));
        try {
            wormBookService.saveBook(book);
        } catch (RuntimeException ex) {
            return "Some problems";
        }
        return "Added";
    }

    @ShellMethod(value = "Update book into books table.", key = {"ub", "u book"})
    public String updateBook(@ShellOption({"-i"}) Long id,
                             @ShellOption(value = {"-t"}, defaultValue = "title") String title,
                             @ShellOption(value = {"-g"}, defaultValue = "0") Long genreId,
                             @ShellOption(value = {"-a"}, arity = 1, defaultValue = "0") long[] authors) {
        String titleOfBook;
        Genre genre;
        if (title.equals("title"))
            titleOfBook = null;
        else titleOfBook = title;
        if (genreId == 0)
            genre = new Genre();
        else genre = new Genre(genreId, null);

        Book book = new Book(id, titleOfBook, genre);
        if (authors.length == 1 && authors[0] == 0) book.setAuthor(Collections.EMPTY_LIST);
        else book.setAuthor(Arrays.stream(authors).mapToObj(Author::new).collect(Collectors.toList()));
        try {
            wormBookService.updateBook(book);
        } catch (NoDataWithThisIdException ex) {
            return ex.getLocalizedMessage();
        }
        return "Updated";
    }

    @ShellMethod(value = "Delete Author from book using author id and book id.", key = {"dba", "db author"})
    public String deleteAuthor(@ShellOption({"-a"}) Long authorId,
                               @ShellOption({"-b"}) Long bookId) {
        try {
            wormBookService.deleteAuthorFromBook(authorId, bookId);
            return "Deleted";
        } catch (NoDataWithThisIdException ex) {
            return ex.getLocalizedMessage();
        }
    }

    @ShellMethod(value = "Delete book from table using id.", key = {"db", "d book"})
    public String deleteBook(@ShellOption({"-i"}) Long id) {
        wormBookService.deleteBook(id);
        return "Deleted";
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
