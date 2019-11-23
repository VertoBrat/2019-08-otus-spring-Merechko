package ru.photorex.hw13.service;

import ru.photorex.hw13.to.BookTo;
import ru.photorex.hw13.to.Filter;

import java.util.List;

public interface LibraryWormBookService {

    List<BookTo> findAllBooks();

    void deleteBook(String id);

    BookTo findBookById(String id);

    BookTo updateSaveBook(BookTo to);

    List<BookTo> filteredBooks(Filter filter);
}
