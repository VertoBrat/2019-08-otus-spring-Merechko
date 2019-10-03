package ru.photorex.hw7.service;

import ru.photorex.hw7.model.Author;
import ru.photorex.hw7.model.Book;

import java.util.List;

public interface LibraryWormBookService {

    Book getBookById(Long id);

    List<Book> getAllBooks();

    List<Book> getBooksByAuthor(Author author);

    void deleteBook(Long id);

    Book saveBook(Book book);

    Book updateBook(Book book);

    void deleteAuthorFromBook(Long authorId, Long bookId);
}
