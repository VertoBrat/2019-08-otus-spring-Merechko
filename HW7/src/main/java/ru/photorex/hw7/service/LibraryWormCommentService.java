package ru.photorex.hw7.service;

import ru.photorex.hw7.model.Comment;

import java.util.List;

public interface LibraryWormCommentService {

    List<Comment> getAllCommentsByBook(Long bookId);

    Comment saveComment(Comment comment);

    void deleteComment(Long id);
}
