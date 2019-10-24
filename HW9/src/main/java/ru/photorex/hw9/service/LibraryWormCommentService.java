package ru.photorex.hw9.service;

import ru.photorex.hw9.to.CommentTo;


public interface LibraryWormCommentService {

    void deleteComment(String commentId);

    CommentTo saveComment(String bookId, String commentText);

    CommentTo updateComment(String commentId, String newCommentText);

    CommentTo findCommentById(String id);
}
