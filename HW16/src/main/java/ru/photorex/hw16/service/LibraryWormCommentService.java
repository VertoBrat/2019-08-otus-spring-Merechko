package ru.photorex.hw16.service;

import ru.photorex.hw16.model.User;
import ru.photorex.hw16.to.CommentTo;


public interface LibraryWormCommentService {

    void deleteComment(String commentId);

    CommentTo saveComment(String bookId, String commentText, User user);

    CommentTo updateComment(String commentId, String newCommentText);

    CommentTo findCommentById(String id);
}
