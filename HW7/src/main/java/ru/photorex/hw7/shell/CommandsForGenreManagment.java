package ru.photorex.hw7.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
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
        Genre genre = wormGenreService.getGenreById(id);
        if (genre != null)
            printTable(Collections.singletonList(genre));
        else console.printString("No genre with id = " + id);
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
        return wormGenreService.saveGenre(genre) != null ? "Added" : "SomeProblem";
    }

    @ShellMethod(value = "Update genre into table.", key = {"ug", "u genre"})
    public String updateGenre(@ShellOption({"-i"}) Long id,
                              @ShellOption({"-n"}) String name) {
        Genre genre = new Genre(id, name);
        try {
            wormGenreService.saveGenre(genre);
        } catch (NoDataWithThisIdException e) {
            return e.getLocalizedMessage();
        }
        return "Successful";
    }

    @ShellMethod(value = "Delete genre from table.", key = {"dg", "d genre"})
    public String deleteGenre(@ShellOption({"-i"}) Long id) {
        try {
            if (wormGenreService.deleteGenreById(id))
                return "Successful";
        } catch (DataAccessException ex) {
            return  "There are few book of this genre, you cant remove it";
        }
        return "There is no genre with id = " + id;
    }

    private void printTable(List<Genre> genres) {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Id");
        headers.put("name", "Name");
        tableBuilder.build(genres, headers);
    }
}
