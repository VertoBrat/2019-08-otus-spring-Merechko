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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryWormBookServiceImpl implements LibraryWormBookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findWithAuthorsById(id).orElseThrow(() -> new NoDataWithThisIdException(id));
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
        setMutableProperties(book, bookDb);
        return bookDb;
    }

    @Override
    @Transactional
    public void deleteAuthorFromBook(Long authorId, Long bookId) {
        Book book = bookRepository.findWithAuthorsById(bookId).orElseThrow(() -> new NoDataWithThisIdException(bookId));
        Author author = authorRepository.getOne(authorId);
        book.removeAuthor(author);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    private void setMutableProperties(Book newBook, Book updatableBook) {
        if (newBook.getTitle() != null)
            updatableBook.setTitle(newBook.getTitle());
        if (newBook.getGenre().getId() != null)
            updatableBook.setGenre(newBook.getGenre());
        if (!newBook.getAuthor().isEmpty()) {
            List<Author> actualAuthor = newBook.getAuthor().stream()
                    .filter(a -> !updatableBook.getAuthor().contains(a))
                    .collect(Collectors.toList());
            updatableBook.getAuthor().addAll(actualAuthor);
        }
    }
}
