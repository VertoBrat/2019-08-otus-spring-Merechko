package ru.photorex.hw7.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.hw7.exception.NoDataWithThisIdException;
import ru.photorex.hw7.model.Book;
import ru.photorex.hw7.model.Comment;
import ru.photorex.hw7.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryWormCommentServiceImpl implements LibraryWormCommentService {

    private final CommentRepository commentRepository;

    @Override
    public List<Comment> getAllCommentsByBook(Long bookId) {
        return commentRepository.findCommentsByBookOrderById(new Book(bookId));
    }

    @Override
    @Transactional
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Comment updateComment(Comment comment) {
        Comment commentDb = commentRepository.findById(comment.getId()).orElseThrow(() -> new NoDataWithThisIdException(comment.getId()));
        commentDb.setText(comment.getText());
        return commentDb;
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
