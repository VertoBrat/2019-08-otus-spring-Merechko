package ru.photorex.hw7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.photorex.hw7.model.Book;
import ru.photorex.hw7.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentsByBook(Book book);
}
