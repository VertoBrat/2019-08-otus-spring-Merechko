package ru.photorex.hw8.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.photorex.hw8.exception.NoDataWithThisIdException;
import ru.photorex.hw8.service.IOService;
import ru.photorex.hw8.service.LibraryWormGenreService;

import java.util.LinkedHashMap;
import java.util.Set;

@ShellComponent
@RequiredArgsConstructor
public class CommandForGenreManagement extends LibraryCommands {

    private final LibraryWormGenreService wormGenreService;
    private final IOService console;

    @ShellMethod(value = "Display genres.", key = {"tg", "t genres"})
    public void displayGenres() {
        Set<String> genres = wormGenreService.findAllGenres();
        console.printString(genres.toString());
    }

    @ShellMethod(value = "Insert new genre to the book by id.", key = {"ibg", "ub genre"})
    public void saveGenreBook(@ShellOption({"-i"}) String bookId,
                              @ShellOption({"-g"}) String genre) {
        try {
            wormGenreService.saveGenre(bookId, genre);
            console.printString(SUCCESS_OPERATION);
        } catch (NoDataWithThisIdException ex) {
            console.printString(ex.getLocalizedMessage());
        }
    }

    @ShellMethod(value = "Delete genre from book.", key = {"dbg", "db genre"})
    public void deleteGenre(@ShellOption({"-b"}) String bookId,
                            @ShellOption({"-g"}) String genre) {
        try {
            wormGenreService.deleteGenre(bookId, genre);
            console.printString(SUCCESS_OPERATION);
        } catch (NoDataWithThisIdException ex) {
            console.printString(ex.getLocalizedMessage());
        }
    }
}
