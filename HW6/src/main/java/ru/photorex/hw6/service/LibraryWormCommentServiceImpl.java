package ru.photorex.hw6.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.hw6.model.Book;
import ru.photorex.hw6.model.Comment;
import ru.photorex.hw6.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryWormCommentServiceImpl implements LibraryWormCommentService {

    private final CommentRepository repository;

    @Override
    public List<Comment> getAllCommentsByBook(Long bookId) {
        return repository.getAllByBook(bookId);
    }

    @Override
    @Transactional
    public Comment saveComment(Comment comment) {
        return repository.save(comment);
    }

    @Override
    @Transactional
    public boolean deleteComment(Long id) {
        return repository.delete(id);
    }
}
