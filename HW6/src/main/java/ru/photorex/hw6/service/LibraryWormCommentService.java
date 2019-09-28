package ru.photorex.hw6.service;

import ru.photorex.hw6.model.Book;
import ru.photorex.hw6.model.Comment;

import java.util.List;

public interface LibraryWormCommentService {

    List<Comment> getAllCommentsByBook(Long bookId);

    Comment saveComment(Comment comment);

    boolean deleteComment(Long id);
}
