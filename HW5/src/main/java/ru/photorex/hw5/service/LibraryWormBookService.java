package ru.photorex.hw5.service;

import ru.photorex.hw5.model.Author;
import ru.photorex.hw5.model.Book;

import java.util.List;

public interface LibraryWormBookService {

    Book getBookById(Long id);

    List<Book> getAllBooks();

    List<Book> getBooksByAuthor(Author author);
}
