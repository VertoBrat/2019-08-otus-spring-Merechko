package ru.photorex.hw8.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.photorex.hw8.exception.NoDataWithThisIdException;
import ru.photorex.hw8.model.Author;
import ru.photorex.hw8.service.IOService;
import ru.photorex.hw8.service.LibraryWormAuthorService;

import java.util.LinkedHashMap;
import java.util.Set;

@ShellComponent
@RequiredArgsConstructor
public class CommandForAuthorManagement extends LibraryCommands {

    private final LibraryWormAuthorService wormAuthorService;
    private final ShellTableBuilder tableBuilder;
    private final IOService console;

    @ShellMethod(value = "Display all authors.", key = {"ta", "t authors"})
    public void displayAuthors() {
        Set<Author> authors = wormAuthorService.findAllAuthors();
        printTable(authors);
    }

    @ShellMethod(value = "Insert new Author to the book.", key = {"iba", "ib author"})
    public void saveAuthor(@ShellOption({"-i"}) String bookId,
                           @ShellOption(value = {"-a"}, arity = 2) String[] authorFullName) {
        try {
            wormAuthorService.saveAuthor(bookId, new Author(authorFullName[0], authorFullName[1]));
            console.printString(SUCCESS_OPERATION);
        } catch (NoDataWithThisIdException ex) {
            console.printString(ex.getLocalizedMessage());
        }
    }

    @ShellMethod(value = "Delete author from book.", key = {"dba", "db author"})
    public void deleteAuthor(@ShellOption({"-i"}) String bookId,
                             @ShellOption(value = {"-a"}, arity = 2) String[] authorFullName) {
        try {
            wormAuthorService.deleteAuthor(bookId, new Author(authorFullName[0], authorFullName[1]));
            console.printString(SUCCESS_OPERATION);
        } catch (NoDataWithThisIdException ex) {
            console.printString(ex.getLocalizedMessage());
        }
    }

    private void printTable(Set<Author> authors) {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("firstName", "FirstName");
        headers.put("lastName", "LastName");
        tableBuilder.build(authors, headers);
    }
}
