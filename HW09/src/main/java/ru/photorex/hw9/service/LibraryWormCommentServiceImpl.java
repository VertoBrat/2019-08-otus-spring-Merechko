package ru.photorex.hw9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.hw9.exception.NoDataWithThisIdException;
import ru.photorex.hw9.model.Book;
import ru.photorex.hw9.model.Comment;
import ru.photorex.hw9.repository.BookRepository;
import ru.photorex.hw9.repository.CommentRepository;
import ru.photorex.hw9.to.mapper.CommentMapper;
import ru.photorex.hw9.to.CommentTo;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryWormCommentServiceImpl implements LibraryWormCommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final CommentMapper mapper;

    @Override
    public CommentTo findCommentById(String id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NoDataWithThisIdException(id));
        return mapper.toTo(comment);
    }

    @Override
    @Transactional
    public CommentTo saveComment(String bookId, String commentText) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NoDataWithThisIdException(bookId));
        return mapper.toTo(commentRepository.save(new Comment(commentText, book)));
    }

    @Override
    @Transactional
    public CommentTo updateComment(String commentId, String newCommentText) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoDataWithThisIdException(commentId));
        comment.setText(newCommentText);
        return mapper.toTo(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public void deleteComment(String commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoDataWithThisIdException(commentId));
        commentRepository.delete(comment);
    }
}
