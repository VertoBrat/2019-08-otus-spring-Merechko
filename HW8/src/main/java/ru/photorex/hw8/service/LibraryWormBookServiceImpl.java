package ru.photorex.hw8.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.hw8.exception.NoDataWithThisIdException;
import ru.photorex.hw8.model.Author;
import ru.photorex.hw8.model.Book;
import ru.photorex.hw8.repository.BookRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryWormBookServiceImpl implements LibraryWormBookService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book findBookById(String id) {
        return bookRepository.findById(id).orElseThrow(() -> new NoDataWithThisIdException(id));
    }

    @Override
    public List<Book> findBookByAuthor(Author author) {
        return bookRepository.findAllByAuthors(author);
    }

    @Override
    public List<Book> findBookByGenre(String genre) {
        return bookRepository.findAllByGenres(genre);
    }

    @Override
    @Transactional
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book updateTitle(String bookId, String title) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NoDataWithThisIdException(bookId));
        book.setTitle(title);
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteBook(String id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NoDataWithThisIdException(id));
        bookRepository.delete(book);
    }
}
