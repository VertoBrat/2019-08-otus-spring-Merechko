package ru.photorex.hw7.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.photorex.hw7.exception.NoDataWithThisIdException;
import ru.photorex.hw7.model.Genre;
import ru.photorex.hw7.service.IOService;
import ru.photorex.hw7.service.LibraryWormGenreService;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class CommandsForGenreManagment extends LibraryCommands {

    private final LibraryWormGenreService wormGenreService;
    private final ShellTableBuilder tableBuilder;
    private final IOService console;

    @ShellMethod(value = "Display genre by id.", key = {"tgi", "t genre by id"})
    public void displayGenreById(@ShellOption({"-i"}) Long id) {
        try {
            Genre genre = wormGenreService.getGenreById(id);
            printTable(Collections.singletonList(genre));
        } catch (NoDataWithThisIdException ex) {
            console.printString(ex.getLocalizedMessage());
        }
    }

    @ShellMethod(value = "Display all genres from table.", key = {"tg", "t genre"})
    public void displayAllGenres() {
        List<Genre> genres = wormGenreService.getAllGenres();
        if (!genres.isEmpty())
        printTable(genres);
        else console.printString("No genres");
    }

    @ShellMethod(value = "Insert genre into table.", key = {"ig", "i genre"})
    public String insertGenre(@ShellOption({"-n"}) String name) {
        Genre genre = new Genre(null, name);
        wormGenreService.saveGenre(genre);
        return "Added";
    }

    @ShellMethod(value = "Update genre into table.", key = {"ug", "u genre"})
    public String updateGenre(@ShellOption({"-i"}) Long id,
                              @ShellOption({"-n"}) String name) {
        Genre genre = new Genre(id, name);
        try {
            wormGenreService.updateGenre(genre);
        } catch (NoDataWithThisIdException e) {
            return e.getLocalizedMessage();
        }
        return "Successful";
    }

    @ShellMethod(value = "Delete genre from table.", key = {"dg", "d genre"})
    public String deleteGenre(@ShellOption({"-i"}) Long id) {
        wormGenreService.deleteGenreById(id);
        return "Deleted";
    }

    private void printTable(List<Genre> genres) {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Id");
        headers.put("name", "Name");
        tableBuilder.build(genres, headers);
    }
}
