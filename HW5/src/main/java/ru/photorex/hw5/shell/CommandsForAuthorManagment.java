package ru.photorex.hw5.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.photorex.hw5.model.Author;
import ru.photorex.hw5.service.LibraryWormAuthorService;

import java.util.LinkedHashMap;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class CommandsForAuthorManagment {

    private final LibraryWormAuthorService wormAuthorService;
    private final ShellTableBuilder tableBuilder;

    @ShellMethod(value = "Display all authors into library", key = {"ta", "t authors"})
    public void displayAuthors() {
        List<Author> authors = wormAuthorService.getAllAuthors();
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Id");
        headers.put("firstName", "FirstName");
        headers.put("lastName", "LastName");
        tableBuilder.build(authors, headers);
    }

    @ShellMethod(value = "Insert into authors table new author", key = {"ia", "i authors"})
    public String insertAuthor(@ShellOption({"-fn"}) String firstName, @ShellOption({"-ln"}) String lastName) {
        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);
        return wormAuthorService.saveAuthor(author).toString();
    }

    @ShellMethod(value = "Update into authors table", key = {"ua", "u authors"})
    public String updateAuthor(@ShellOption(value = {"-i"}, defaultValue = "0") Long id,
                               @ShellOption({"-fn"}) String firstName,
                               @ShellOption({"-ln"}) String lastName) {
        Author author = new Author();
        if (id == 0) return "id is not insert, check your command";
        author.setId(id);
        author.setFirstName(firstName);
        author.setLastName(lastName);
        Author dbAuthor = wormAuthorService.saveAuthor(author);
        if (dbAuthor == null)
            return "There is no author with id =" + id;
        return dbAuthor.toString();
    }

    @ShellMethod(value = "Delete author from table using id", key = {"da", "d author"})
    public String deleteAuthor(@ShellOption({"-i"}) Long id) {
        if (wormAuthorService.deleteAuthor(id))
            return "Successful";
        return "Some problem";
    }
}
