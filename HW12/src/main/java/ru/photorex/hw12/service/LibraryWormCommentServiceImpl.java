package ru.photorex.hw12.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.hw12.exception.NoDataWithThisIdException;
import ru.photorex.hw12.model.Book;
import ru.photorex.hw12.model.Comment;
import ru.photorex.hw12.model.User;
import ru.photorex.hw12.repository.BookRepository;
import ru.photorex.hw12.repository.CommentRepository;
import ru.photorex.hw12.to.CommentTo;
import ru.photorex.hw12.to.mapper.CommentMapper;
import ru.photorex.hw12.util.LibraryUtil;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryWormCommentServiceImpl implements LibraryWormCommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final CommentMapper mapper;

    @Override
    @HystrixCommand(fallbackMethod = "findSomeComment", commandKey = "comments")
    public CommentTo findCommentById(String id) {
        LibraryUtil.sleepRandomly(5, TimeUnit.SECONDS);
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NoDataWithThisIdException(id));
        return mapper.toTo(comment);
    }

    public CommentTo findSomeComment(String id) {
        return new CommentTo();
    }

    @Override
    @Transactional
    public CommentTo saveComment(String bookId, String commentText, User user) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NoDataWithThisIdException(bookId));
        return mapper.toTo(commentRepository.save(new Comment(commentText, book, user)));
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
