package ru.photorex.hw5.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.photorex.hw5.model.Author;
import ru.photorex.hw5.service.IOService;
import ru.photorex.hw5.service.LibraryWormAuthorService;

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
        } catch (DataAccessException ex) {
            console.printString("No author with id = " + id);
        }
    }

    @ShellMethod(value = "Insert into authors table new author.", key = {"ia", "i author"})
    public String insertAuthor(@ShellOption({"-fn"}) String firstName, @ShellOption({"-ln"}) String lastName) {
        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);
        return wormAuthorService.saveAuthor(author) != null ? "Added" : "Some Problem";
    }

    @ShellMethod(value = "Update into authors table.", key = {"ua", "u authors"})
    public String updateAuthor(@ShellOption(value = {"-i"}) Long id,
                               @ShellOption({"-fn"}) String firstName,
                               @ShellOption({"-ln"}) String lastName) {
        Author author = new Author();
        author.setId(id);
        author.setFirstName(firstName);
        author.setLastName(lastName);
        Author dbAuthor = wormAuthorService.saveAuthor(author);
        if (dbAuthor == null)
            return "There is no author with id =" + id;
        return "Successful";
    }

    @ShellMethod(value = "Delete author from table using id.", key = {"da", "d author"})
    public String deleteAuthor(@ShellOption({"-i"}) Long id) {
        try {
            if (wormAuthorService.deleteAuthor(id))
                return "Successful";
        } catch (DataAccessException ex) {
            return "This Author have books, you cant remove him";
        }
        return "No author with id = " + id;
    }

    private void printTable(List<Author> authors) {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Id");
        headers.put("firstName", "FirstName");
        headers.put("lastName", "LastName");
        tableBuilder.build(authors, headers);
    }
}
