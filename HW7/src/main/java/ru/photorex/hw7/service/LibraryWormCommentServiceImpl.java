package ru.photorex.hw7.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.hw7.model.Book;
import ru.photorex.hw7.model.Comment;
import ru.photorex.hw7.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryWormCommentServiceImpl implements LibraryWormCommentService {

    private final CommentRepository repository;

    @Override
    public List<Comment> getAllCommentsByBook(Long bookId) {
        return repository.findCommentsByBook(new Book(bookId));
    }

    @Override
    @Transactional
    public Comment saveComment(Comment comment) {
        return repository.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        repository.deleteById(id);
    }
}
