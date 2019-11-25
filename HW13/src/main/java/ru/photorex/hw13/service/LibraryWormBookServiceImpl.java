package ru.photorex.hw13.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.hw13.acl.service.AclSupport;
import ru.photorex.hw13.exception.NoDataWithThisIdException;
import ru.photorex.hw13.model.Author;
import ru.photorex.hw13.model.Book;
import ru.photorex.hw13.model.Comment;
import ru.photorex.hw13.repository.BookRepository;
import ru.photorex.hw13.to.BookTo;
import ru.photorex.hw13.to.Filter;
import ru.photorex.hw13.to.mapper.BookMapper;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryWormBookServiceImpl implements LibraryWormBookService {

    private final BookRepository bookRepository;
    private final FilterParserService parserService;
    private final BookMapper mapper;
    private final AclSupport aclSupport;

    @Override
    public List<BookTo> findAllBooks() {
        return mapper.toListTo(bookRepository.findAll());
    }

    @Override
    public BookTo findBookById(String id) {
        Book book = findById(id);
        return mapper.toTo(book);
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'ru.photorex.hw13.model.Book', 'WRITE') or hasRole('ROLE_ADMIN')")
    public BookTo findBookByIdForEdit(String id) {
        return findBookById(id);
    }

    @Override
    @Transactional
    public BookTo updateSaveBook(BookTo to) {
        if (!to.getId().isEmpty()) {
            Book book = findById(to.getId());
            return mapper.toTo(bookRepository.save(mapper.updateBook(to, book)));
        }
        to.setId(null);
        Book dbBook = bookRepository.save(mapper.toEntity(to));
        grantAclCollections(dbBook.getId());
        return mapper.toTo(dbBook);
    }

    @Override
    public List<BookTo> filteredBooks(Filter filter) {
        if (filter.getType().equals("genre"))
            return mapper.toListTo(bookRepository.findAllFilteredPerGenre(filter.getFilterText()));
        else {
            Author author = parserService.parseStringToAuthor(filter.getFilterText());
            return mapper.toListTo(bookRepository.findAllFilteredPerAuthors(author));
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasPermission(#id, 'ru.photorex.hw13.model.Book', 'DELETE')")
    public void deleteBook(String id) {
        Book book = findById(id);
        bookRepository.delete(book);
        aclSupport.deleteFromAcls(Book.class.getTypeName(), id, true);
        for (Comment c : book.getComments()) {
            aclSupport.deleteFromAcls(Comment.class.getTypeName(), c.getId(), true);
        }
    }

    private Book findById(String id) {
        return bookRepository.findById(id).orElseThrow(() -> new NoDataWithThisIdException(id));
    }

    private String getUserNameFromAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    private void grantAclCollections(String id) {
        aclSupport.grantPermissionsToUser(
                Book.class.getTypeName(),
                id,
                getUserNameFromAuth(),
                BasePermission.READ, BasePermission.WRITE, BasePermission.CREATE, BasePermission.DELETE);
    }

}
