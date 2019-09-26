package ru.photorex.hw6.service;

import ru.photorex.hw6.model.Author;
import ru.photorex.hw6.model.Book;

import java.util.List;

public interface LibraryWormBookService {

    Book getBookById(Long id);

    List<Book> getAllBooks();

    List<Book> getBooksByAuthor(Author author);

    boolean deleteBook(Long id);

    Book saveBook(Book book);
}
