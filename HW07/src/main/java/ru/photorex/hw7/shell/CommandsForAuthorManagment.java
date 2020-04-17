package ru.photorex.hw7.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.photorex.hw7.exception.NoDataWithThisIdException;
import ru.photorex.hw7.model.Author;
import ru.photorex.hw7.service.IOService;
import ru.photorex.hw7.service.LibraryWormAuthorService;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class CommandsForAuthorManagment extends LibraryCommands {

    private final LibraryWormAuthorService wormAuthorService;
    private final ShellTableBuilder tableBuilder;
    private final IOService console;

    @ShellMethod(value = "Display all authors into library.", key = {"ta", "t authors"})
    public void displayAuthors() {
        List<Author> authors = wormAuthorService.getAllAuthors();
        printTable(authors);
    }

    @ShellMethod(value = "Display author by id.", key = {"tai", "t authors by id"})
    public void displayAuthorById(@ShellOption({"-i"}) Long id) {
        Author author;
        try {
            author = wormAuthorService.getAuthorById(id);
            printTable(Collections.singletonList(author));
        } catch (NoDataWithThisIdException e) {
            console.printString(e.getLocalizedMessage());
        }
    }

    @ShellMethod(value = "Insert into authors table new author.", key = {"ia", "i author"})
    public String insertAuthor(@ShellOption({"-fn"}) String firstName, @ShellOption({"-ln"}) String lastName) {
        Author author = new Author(null, firstName, lastName);
        wormAuthorService.saveAuthor(author);
        return "Added";
    }

    @ShellMethod(value = "Update into authors table.", key = {"ua", "u authors"})
    public String updateAuthor(@ShellOption(value = {"-i"}) Long id,
                               @ShellOption(value = {"-fn"}, defaultValue = "first_name") String fName,
                               @ShellOption(value = {"-ln"}, defaultValue = "last_name") String lName) {
        String firstName = null;
        String lastName = null;
        if (!fName.equals("first_name"))
            firstName = fName;
        if (!lName.equals("last_name"))
            lastName = lName;
        Author author = new Author(id, firstName, lastName);
        try {
            wormAuthorService.updateAuthor(author);
        } catch (NoDataWithThisIdException e) {
            return e.getLocalizedMessage();
        }
        return "Successful";
    }

    @ShellMethod(value = "Delete author from table using id.", key = {"da", "d author"})
    public String deleteAuthor(@ShellOption({"-i"}) Long id) {
        try {
            wormAuthorService.deleteAuthor(id);
        } catch (EmptyResultDataAccessException ex) {
            return "This Author have books, you cant remove him";
        } catch (DataIntegrityViolationException ex) {
            return "This author have books";
        }
        return "Deleted";
    }

    private void printTable(List<Author> authors) {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Id");
        headers.put("firstName", "FirstName");
        headers.put("lastName", "LastName");
        tableBuilder.build(authors, headers);
    }
}
