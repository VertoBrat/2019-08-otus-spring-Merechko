package ru.photorex.hw8.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.hw8.exception.NoDataWithThisIdException;
import ru.photorex.hw8.model.Book;
import ru.photorex.hw8.model.Comment;
import ru.photorex.hw8.repository.BookRepository;
import ru.photorex.hw8.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryWormCommentServiceImpl implements LibraryWormCommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @Override
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    @Override
    @Transactional
    public Comment saveComment(String bookId, String commentText) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NoDataWithThisIdException(bookId));
        return commentRepository.save(new Comment(commentText, book));
    }

    @Override
    @Transactional
    public void deleteComment(String commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoDataWithThisIdException(commentId));
        commentRepository.delete(comment);
    }
}
