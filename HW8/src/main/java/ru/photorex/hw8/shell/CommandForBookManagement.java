package ru.photorex.hw8.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.photorex.hw8.exception.NoDataWithThisIdException;
import ru.photorex.hw8.model.Author;
import ru.photorex.hw8.model.Book;
import ru.photorex.hw8.service.IOService;
import ru.photorex.hw8.service.LibraryWormBookService;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

@ShellComponent
@RequiredArgsConstructor
public class CommandForBookManagement extends LibraryCommands {

    private final LibraryWormBookService wormBookService;
    private final ShellTableBuilder tableBuilder;
    private final IOService console;

    @ShellMethod(value = "Display all books into library.", key = {"tb", "t books"})
    public void displayBooks() {
        List<Book> books = wormBookService.findAllBooks();
        printTable(books, false);
    }

    @ShellMethod(value = "Display book with comments by Id.", key = {"tbi"})
    public void displayBookById(@ShellOption({"-i"}) String id) {
        try {
            Book book = wormBookService.findBookById(id);
            printTable(Collections.singletonList(book), true);
        } catch (NoDataWithThisIdException ex) {
            console.printString(ex.getLocalizedMessage());
        }
    }

    @ShellMethod(value = "Save book.", key = {"ib", "i book"})
    public void saveBook(@ShellOption({"-t"}) String title,
                         @ShellOption({"-g"}) String genre,
                         @ShellOption(value = {"-a"}, arity = 2) String[] authorFullName) {
        Set<Author> authors = Set.of(new Author(authorFullName[0], authorFullName[1]));
        Book book = new Book(title, Set.of(genre), authors);
        wormBookService.saveBook(book);
    }

    @ShellMethod(value = "Delete book by id.", key = {"db", "d book"})
    public void deleteBook(@ShellOption({"-i"}) String id) {
        try {
            wormBookService.deleteBook(id);
            console.printString(SUCCESS_OPERATION);
        } catch (NoDataWithThisIdException ex) {
            console.printString(ex.getLocalizedMessage());
        }
    }

    private void printTable(List<Book> books, boolean withComments) {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Id");
        headers.put("title", "Title");
        headers.put("genres", "Genre");
        headers.put("authors", "Authors");
        if (withComments)
            headers.put("comments", "Comments");
        tableBuilder.build(books, headers);
    }
}
