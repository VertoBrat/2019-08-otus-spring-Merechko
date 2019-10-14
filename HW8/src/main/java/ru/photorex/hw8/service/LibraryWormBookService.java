package ru.photorex.hw8.service;

import ru.photorex.hw8.model.Book;

import java.util.List;

public interface LibraryWormBookService {

    List<Book> findAllBooks();

    void deleteBook(String id);

    Book findBookById(String id);

    Book saveBook(Book book);
}
