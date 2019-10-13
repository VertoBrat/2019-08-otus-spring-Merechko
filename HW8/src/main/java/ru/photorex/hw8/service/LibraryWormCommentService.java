package ru.photorex.hw8.service;

import ru.photorex.hw8.model.Comment;

import java.util.List;

public interface LibraryWormCommentService {
    List<Comment> findAllComments();
    void deleteComment(String commentId);
}
