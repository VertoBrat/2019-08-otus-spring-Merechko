package ru.photorex.hw13.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.hw13.acl.service.AclSupport;
import ru.photorex.hw13.exception.NoDataWithThisIdException;
import ru.photorex.hw13.model.Book;
import ru.photorex.hw13.model.Comment;
import ru.photorex.hw13.model.User;
import ru.photorex.hw13.repository.BookRepository;
import ru.photorex.hw13.repository.CommentRepository;
import ru.photorex.hw13.to.CommentTo;
import ru.photorex.hw13.to.mapper.CommentMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryWormCommentServiceImpl implements LibraryWormCommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final CommentMapper mapper;
    private final AclSupport aclSupport;

    @Override
    public CommentTo findCommentById(String id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NoDataWithThisIdException(id));
        return mapper.toTo(comment);
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'ru.photorex.hw13.model.Comment', 'WRITE')")
    public CommentTo findCommentByIdForEdit(String id) {
        return findCommentById(id);
    }

    @Override
    @Transactional
    public CommentTo saveComment(String bookId, String commentText, User user) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NoDataWithThisIdException(bookId));
        Comment dbComment = commentRepository.save(new Comment(commentText, book, user));
        grantAclCollections(dbComment.getId());
        return mapper.toTo(dbComment);
    }

    @Override
    @Transactional
    @PreAuthorize("hasPermission(#commentId, 'ru.photorex.hw13.model.Comment', 'WRITE')")
    public CommentTo updateComment(String commentId, String newCommentText) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoDataWithThisIdException(commentId));
        comment.setText(newCommentText);
        return mapper.toTo(commentRepository.save(comment));
    }

    @Override
    @Transactional
    @PreAuthorize("hasPermission(#commentId, 'ru.photorex.hw13.model.Comment', 'DELETE')")
    public void deleteComment(String commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoDataWithThisIdException(commentId));
        commentRepository.delete(comment);
    }

    private String getUserNameFromAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    private void grantAclCollections(String id) {
        aclSupport.grantPermissionsToUser(
                Comment.class.getTypeName(),
                id,
                getUserNameFromAuth(),
                BasePermission.READ, BasePermission.WRITE, BasePermission.CREATE, BasePermission.DELETE);
    }
}
