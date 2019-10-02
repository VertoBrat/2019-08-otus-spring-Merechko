package ru.photorex.hw7.repository;

import ru.photorex.hw7.model.Comment;

import java.util.List;

public interface CommentRepository {

    List<Comment> getAllByBook(Long bookId);

    Comment save(Comment comment);

    boolean delete(Long id);
}
