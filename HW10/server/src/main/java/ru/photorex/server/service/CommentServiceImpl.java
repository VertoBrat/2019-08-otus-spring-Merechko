package ru.photorex.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.server.exception.NoDataWithThisIdException;
import ru.photorex.server.model.Book;
import ru.photorex.server.model.Comment;
import ru.photorex.server.repository.BookRepository;
import ru.photorex.server.repository.CommentRepository;
import ru.photorex.server.to.CommentTo;
import ru.photorex.server.to.mapper.CommentMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final CommentMapper mapper;

    @Override
    public CommentTo saveComment(String bookId, String commentText) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NoDataWithThisIdException(bookId));
        return mapper.toTo(commentRepository.save(new Comment(commentText, book)));
    }

    @Override
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
