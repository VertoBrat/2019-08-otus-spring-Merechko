package ru.photorex.hw8.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.photorex.hw8.exception.NoDataWithThisIdException;
import ru.photorex.hw8.model.Comment;
import ru.photorex.hw8.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryWormCommentServiceImpl implements LibraryWormCommentService {

    private final CommentRepository commentRepository;

    @Override
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public void deleteComment(String commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoDataWithThisIdException(commentId));
        commentRepository.delete(comment);
    }
}
