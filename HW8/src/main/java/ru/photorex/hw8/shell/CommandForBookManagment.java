package ru.photorex.hw8.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.photorex.hw8.model.Book;
import ru.photorex.hw8.service.IOService;
import ru.photorex.hw8.service.LibraryWormBookService;
import java.util.LinkedHashMap;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class CommandForBookManagment extends LibraryCommands {

    private final LibraryWormBookService wormBookService;
    private final ShellTableBuilder tableBuilder;
    private final IOService console;

    @ShellMethod(value = "Display all books into library.", key = {"tb", "t books"})
    public void displayBooks() {

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
