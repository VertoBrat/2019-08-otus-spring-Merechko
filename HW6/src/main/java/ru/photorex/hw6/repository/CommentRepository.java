package ru.photorex.hw6.repository;

import ru.photorex.hw6.model.Book;
import ru.photorex.hw6.model.Comment;

import java.util.List;

public interface CommentRepository {

    List<Comment> getAllByBook(Long bookId);

    Comment save(Comment comment);

    boolean delete(Long id);
}
