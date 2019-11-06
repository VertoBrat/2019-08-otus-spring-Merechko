package ru.photorex.server.service;

import ru.photorex.server.to.CommentTo;

public interface CommentService {

    CommentTo saveComment(String bookId, String commentText);

    CommentTo updateComment(String commentId, String newCommentText);

    void deleteComment(String id);
}
