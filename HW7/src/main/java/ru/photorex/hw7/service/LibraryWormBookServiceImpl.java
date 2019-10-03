package ru.photorex.hw7.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.hw7.exception.NoDataWithThisIdException;
import ru.photorex.hw7.model.Author;
import ru.photorex.hw7.model.Book;
import ru.photorex.hw7.repository.AuthorRepository;
import ru.photorex.hw7.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryWormBookServiceImpl implements LibraryWormBookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findWithAuthorsById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll(Sort.by("id"));
    }

    @Override
    public List<Book> getBooksByAuthor(Author author) {
        return bookRepository.findBooksByAuthorOrderById(author);
    }

    @Override
    @Transactional
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book updateBook(Book book) {
        Book bookDb = bookRepository.findById(book.getId()).orElseThrow(() -> new NoDataWithThisIdException(book.getId()));
        if (book.getTitle() != null)
            bookDb.setTitle(book.getTitle());
        if (book.getGenre().getId() != null)
            bookDb.setGenre(book.getGenre());
        if (!book.getAuthor().isEmpty())
            bookDb.getAuthor().addAll(book.getAuthor());
        return bookDb;
    }

    @Override
    @Transactional
    public void deleteAuthorFromBook(Long authorId, Long bookId) {
        authorRepository.findById(authorId).orElseThrow(() -> new  NoDataWithThisIdException(authorId));
        bookRepository.findById(bookId).orElseThrow(() -> new NoDataWithThisIdException(bookId));
        bookRepository.deleteAuthor(authorId, bookId);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
