package ru.photorex.hw8.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.photorex.hw8.exception.NoDataWithThisIdException;
import ru.photorex.hw8.model.Book;
import ru.photorex.hw8.model.Comment;
import ru.photorex.hw8.service.IOService;
import ru.photorex.hw8.service.LibraryWormCommentService;

import java.util.LinkedHashMap;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class CommandForCommentManagement extends LibraryCommands {

    private final LibraryWormCommentService wormCommentService;
    private final ShellTableBuilder tableBuilder;
    private final IOService console;

    @ShellMethod(value = "Display all comments.", key = {"tc", "t comments"})
    public void displayComments() {
        List<Comment> comments = wormCommentService.findAllComments();
        printTable(comments);
    }

    @ShellMethod(value = "Save comment with book id.", key = {"ic", "i comment"})
    public void saveComment(@ShellOption({"-b"}) String bookId,
                            @ShellOption({"-t"}) String commentText) {
        try {
            wormCommentService.saveComment(bookId, commentText);
            console.printString("Success");
        } catch (NoDataWithThisIdException ex) {
            console.printString(ex.getLocalizedMessage());
        }
    }

    @ShellMethod(value = "Update comment by id.", key = {"uc", "u comment"})
    public void updateComment(@ShellOption({"-i"}) String commentId,
                              @ShellOption({"-t"}) String newCommentText) {
        try {
            wormCommentService.updateComment(commentId, newCommentText);
            console.printString("Success");
        } catch (NoDataWithThisIdException ex) {
            console.printString(ex.getLocalizedMessage());
        }
    }

    @ShellMethod(value = "Delete comment by id.", key = {"dc"})
    public void deleteCommentById(@ShellOption({"-i"}) String id) {
        try {
            wormCommentService.deleteComment(id);
            console.printString("Success");
        } catch (NoDataWithThisIdException ex) {
            console.printString(ex.getLocalizedMessage());
        }
    }

    private void printTable(List<Comment> comments) {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Id");
        headers.put("text", "Text");
        tableBuilder.build(comments, headers);
    }
}
