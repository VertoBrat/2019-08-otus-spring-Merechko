package ru.photorex.hw5.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.photorex.hw5.model.Author;
import ru.photorex.hw5.service.LibraryWormAuthorService;

import java.util.LinkedHashMap;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class CommandsForLibraryApplication {

    private final LibraryWormAuthorService wormAuthorService;
    private final ShellTableBuilder tableBuilder;

    @ShellMethod(value = "Display all authors into library", key = {"da", "d authors"})
    public void displayAuthors() {
        List<Author> authors = wormAuthorService.getAllAuthors();
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Id");
        headers.put("firstName", "FirstName");
        headers.put("lastName", "LastName");
        tableBuilder.build(authors, headers);
    }
}
