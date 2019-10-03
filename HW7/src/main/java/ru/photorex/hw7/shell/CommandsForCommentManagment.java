package ru.photorex.hw7.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.photorex.hw7.exception.NoDataWithThisIdException;
import ru.photorex.hw7.model.Book;
import ru.photorex.hw7.model.Comment;
import ru.photorex.hw7.service.IOService;
import ru.photorex.hw7.service.LibraryWormCommentService;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class CommandsForCommentManagment extends LibraryCommands {

    private final LibraryWormCommentService wormCommentService;
    private final ShellTableBuilder tableBuilder;
    private final IOService console;

    @ShellMethod(value = "Display comments by book id.", key = {"tc", "t comments"})
    public void displayCommentsByBook(@ShellOption({"-b"}) Long bookId) {
        List<Comment> comments = wormCommentService.getAllCommentsByBook(bookId);
        if (!comments.isEmpty()) {
            printTable(comments);
        } else console.printString("No comments");
    }

    @ShellMethod(value = "Save new comment.", key = {"ic", "i comment"})
    public String insertComment(@ShellOption({"-b"}) Long bookId,
                                @ShellOption({"-t"}) String text) {
        Comment comment = new Comment(null, text, new Book(bookId), LocalDateTime.now());
        wormCommentService.saveComment(comment);
        return "Added";
    }

    @ShellMethod(value = "Update some comment by id.", key = {"uc", "u comment"})
    public String updateComment(@ShellOption({"-i"}) Long commentId,
                                @ShellOption({"-t"}) String newText) {
        Comment comment = new Comment(commentId, newText, null, LocalDateTime.now());
        try {
            wormCommentService.updateComment(comment);
        } catch (NoDataWithThisIdException e) {
            return e.getLocalizedMessage();
        }
        return "Updated";
    }

    @ShellMethod(value = "Delete comment.", key = {"dc", "d comment"})
    public String deleteComment(@ShellOption({"-i"}) Long id) {
        wormCommentService.deleteComment(id);
        return "Deleted";
    }

    private void printTable(List<Comment> comments) {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Id");
        headers.put("text", "Text");
        headers.put("dateTime", "Date");
        tableBuilder.build(comments, headers);
    }
}
