package ru.photorex.hw8.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.hw8.exception.NoDataWithThisIdException;
import ru.photorex.hw8.model.Author;
import ru.photorex.hw8.model.Book;
import ru.photorex.hw8.repository.BookRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryWormAuthorServiceImpl implements LibraryWormAuthorService {

    private final BookRepository bookRepository;

    @Override
    public Set<Author> findAllAuthors() {
        return bookRepository.findAllAuthors();
    }

    @Override
    @Transactional
    public void saveAuthor(String bookId, Author author) {
        Book book = findBook(bookId);
        book.getAuthors().add(author);
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteAuthor(String bookId, Author author) {
        Book book = findBook(bookId);
        Set<Author> authors = book.getAuthors().stream().filter(a -> !a.equals(author)).collect(Collectors.toSet());
        book.setAuthors(authors);
        bookRepository.save(book);
    }

    private Book findBook(String bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new NoDataWithThisIdException(bookId));
    }
}
