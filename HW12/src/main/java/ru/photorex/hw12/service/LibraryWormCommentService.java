package ru.photorex.hw12.service;

import ru.photorex.hw12.model.User;
import ru.photorex.hw12.to.CommentTo;


public interface LibraryWormCommentService {

    void deleteComment(String commentId);

    CommentTo saveComment(String bookId, String commentText, User user);

    CommentTo updateComment(String commentId, String newCommentText);

    CommentTo findCommentById(String id);
}
